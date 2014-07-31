package br.com.brokenbits.jopts;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class implements the argument parser.
 * 
 * @author FJTC
 * @param <T> The type of the POJO annotated with the argument definitions.
 */
public class ArgumentParser<T> {
	
	/**
	 * List of argument definitions.
	 */
	private HashMap<String, ArgumentDefinition> argDefs = new HashMap<String, ArgumentDefinition>();
	
	/**
	 * This list keeps track of the conflicting arguments. This is used to generate better help messages.
	 */
	private HashMap<String, List<String>> conflictList = new HashMap<String, List<String>>();
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param type The type of the annotated POJO.
	 * @throws InvalidArgumentDefinitionException If the argument definitions are invalid.
	 */
	public ArgumentParser(Class<T> type) throws InvalidArgumentDefinitionException {
		
		// List methods
		for (Method m: type.getMethods()){
			Argument a = m.getAnnotation(Argument.class);
			if (a != null) {
				processMethod(m, a);
			} else {
				Arguments args = m.getAnnotation(Arguments.class);
				if (args != null) {
					if (args.arguments() == null) {
						throw new InvalidArgumentDefinitionException("The annotation arguments must contain at least one  argument.");
					} else {
						for (Argument arg: args.arguments()) {
							processMethod(m, arg);			
						}
					}
				}
			}
		}
	}
	
	private void processMethod(Method m, Argument a) throws InvalidArgumentDefinitionException {
		try {
			ArgumentDefinition def = new ArgumentDefinition(m, (Argument)a);
			
			// Test for the unnamed first
			if (argDefs.containsKey(def.getName())) {
				throw new IllegalStateException("Duplicated argument definition.");
			} else {
				argDefs.put(def.getName(), def);
			}
			
			// Keep a list of the conflicting arguments
			if (def.isUnique()) {
				List<String> conflicts = conflictList.get(def.getUniqueKey());
				if (conflicts == null) {
					conflicts = new ArrayList<String>();
					conflictList.put(def.getUniqueKey(), conflicts);
				}
				conflicts.add(def.getName());
			}					
		} catch (Exception e) {
			throw new InvalidArgumentDefinitionException(((Argument) a).name(), 
					String.format("Invalid argument definition found in the declaration of %1$s.%2$s().",
							m.getDeclaringClass().getName(),
							m.getName()), e);
		}
	}
	
	/**
	 * Process the argument line and fills the DAO instance with the 
	 * 
	 * @param args The list of arguments.
	 * @param instance The instance to be filled.
	 * @throws IllegalArgumentException If one or more parameters are not correct.
	 * @throws ArgumentParserException If the arguments could not be parsed.
	 */
	public void process(String args[], T instance) throws IllegalArgumentException, ArgumentParserException {
		process(args, 0, args.length, instance);
	}
	
	/**
	 * Process the argument line and fills the DAO instance with the 
	 * 
	 * @param args The list of arguments.
	 * @param offs The first argument to be parsed.
	 * @param count The number of arguments to be parsed.
	 * @param instance The instance to be filled.
	 * @throws IllegalArgumentException If one or more parameters are not correct.
	 * @throws ArgumentParserException If the arguments could not be parsed.
	 */
	public void process(String args[], int offs, int count, T instance) throws IllegalArgumentException, ArgumentParserException {
		String arg;
		String val;
		ArgumentDefinition def;
		ArgumentParserContext context = new ArgumentParserContext(args, offs, count);
		
		while (context.hasNext()) {
			// Fetch the first part
			arg = context.getNext();
			// Look for the parameter
			def = argDefs.get(arg);
			if (def != null) {
				if (def.hasParameter()) {
					val = context.getNext();
					if (val == null) {
						// Missing argument
						throw new MissingArgumentParameterException(arg, "Missing argument.");
					}
				} else {
					val = null;
				}
			} else {
				// Unnamed value
				def = argDefs.get(null);
				if (def == null) {
					// Unnamed argument is not acceptable
					throw new UnknownArgumentException(arg, "Unknown argumented because unnamed argument is not defined.");					
				}
				val = arg;
			}
			
			// Validate uniqueness
			context.validateUnique(def);	

			// Call the method!
			def.invokeMethod(instance, val);
		}
	}
	
	/**
	 * Checks if this parser allows unnamed arguments.
	 * 
	 * @return true if it allows unnamed arguments or false otherwise.
	 */
	public boolean hasUnnamedArgument(){
		return (argDefs.get(null) != null);
	}
	
	/**
	 * Checks if this parser defines arguments.
	 *  
	 * @return true if it define arguments or false otherwise.
	 */
	public boolean hasArguments(){
		return (this.argDefs.size() > 0);
	}
	
	/**
	 * Generates a list of argument help information.
	 * 
	 * @param res The resource to be used. Can be null if no resource is available.
	 * @return The list of arguments sorted by name.
	 */
	public List<ArgumentHelp> generateHelp(ResourceBundle res) {
		ArrayList<ArgumentHelp> list = new ArrayList<ArgumentHelp>();
		String desc;

		for (Map.Entry<String, ArgumentDefinition> e: this.argDefs.entrySet()) {
			ArgumentDefinition d = e.getValue();
			ArgumentHelp h = new ArgumentHelp();
			
			// Set the name
			h.setName(d.getName());

			// Set the type
			h.setType(d.getType());

			// Set the description
			desc = d.getDescription();
			if (res != null) {
				if (d.getResourceName() != null) {
					String s = res.getString(d.getResourceName());
					if (s == null) {
						desc = d.getResourceName();
					}
				}
			}
			h.setDescription(desc);
			
			// Set the conflict list
			if (d.isUnique()) {
				List<String> c = this.conflictList.get(d.getUniqueKey());
				if (c != null) {
					h.setConflicts(c);
				}			
			}
		}
		
		// Sort this list
		Collections.sort(list);
		
		return list;		
	}
}

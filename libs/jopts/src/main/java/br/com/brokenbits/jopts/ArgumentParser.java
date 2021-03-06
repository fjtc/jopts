/*
 * Copyright (c) 2014, Fabio Jun Takada Chino
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *   
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package br.com.brokenbits.jopts;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
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
					if (args.value() == null) {
						throw new InvalidArgumentDefinitionException("The annotation arguments must contain at least one  argument.");
					} else {
						for (Argument arg: args.value()) {
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
			
			if (a.name().equals("--")) {
				new IllegalArgumentException("-- is a reserved argument name.");
			}
			
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
	 * @deprecated Use parse(String[],T) instead of this method.
	 */
	@Deprecated
	public void process(String args[], T instance) throws IllegalArgumentException, ArgumentParserException {
		parse(args, instance);
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
	 * @deprecated Use parse(String[],int,int,T) instead of this method.
	 */
	@Deprecated	
	public void process(String args[], int offs, int count, T instance) throws IllegalArgumentException, ArgumentParserException {
		parse(args, offs, count, instance);
	}
	
	/**
	 * Process the argument line and fills the DAO instance with the 
	 * 
	 * @param args The list of arguments.
	 * @param instance The instance to be filled.
	 * @throws IllegalArgumentException If one or more parameters are not correct.
	 * @throws ArgumentParserException If the arguments could not be parsed.
	 * @since 0.0.3-SNAPSHOT
	 */
	public void parse(String args[], T instance) throws IllegalArgumentException, ArgumentParserException {
		parse(args, 0, args.length, instance);
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
	 * @since 0.0.3-SNAPSHOT
	 */
	public void parse(String args[], int offs, int count, T instance) throws IllegalArgumentException, ArgumentParserException {
		String arg;
		String val;
		ArgumentDefinition def;
		ArgumentParserContext context = new ArgumentParserContext(args, offs, count);
		boolean allUnnamed = false;
		
		while (context.hasNext()) {
			// Fetch the first part
			arg = context.getNext();
			if (allUnnamed) {
				// Unnamed values only
				def = argDefs.get(null);
				if (def == null) {
					// Unnamed argument is not acceptable
					throw new UnknownArgumentException(arg, "Unknown argumented because unnamed argument is not defined.");					
				}
				val = arg;
				
				// Validate uniqueness
				context.validateUnique(def);	
	
				// Call the method!
				def.invokeMethod(instance, val);				
			} else {
				if (arg.equals("--")) {
					allUnnamed = true;
				} else {
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
	 * Generates a list of argument help information. The list is sorted using the case sensitive
	 * lexicographic order. If the unnamed argument is present, it will always be the first entry
	 * in the list.  
	 * 
	 * @param res The resource to be used. Can be null if no resource is available.
	 * @return The list of arguments sorted by name.
	 */
	public List<ArgumentHelp> generateHelp(ResourceBundle res) {
		ArrayList<ArgumentHelp> list = new ArrayList<ArgumentHelp>();

		for (Map.Entry<String, ArgumentDefinition> e: this.argDefs.entrySet()) {
			ArgumentDefinition d = e.getValue();
			ArgumentHelp h = new ArgumentHelp();
			
			// Set the name
			h.setName(d.getName());

			// Set the type
			h.setType(d.getType());

			// Set the description
			h.setDescription(getResourceString(res, d));
			
			// Set the conflict list
			if (d.isUnique()) {
				List<String> c = this.conflictList.get(d.getUniqueKey());
				if (c != null) {
					h.setConflicts(c);
				}			
			}
			
			list.add(h);
		}
		
		// Sort this list
		Collections.sort(list);
		
		return list;		
	}
	
	private String getResourceString(ResourceBundle res, ArgumentDefinition d){
		String s;
		
		s = d.getDescription();
		if ((res != null) && (d.getResourceName() != null)) {
			try {
				s = res.getString(d.getResourceName());
			} catch (MissingResourceException e) {
				s = d.getDescription();
			}
		}
		return s;
	}
}

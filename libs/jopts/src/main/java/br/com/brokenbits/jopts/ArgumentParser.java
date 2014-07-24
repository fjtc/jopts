package br.com.brokenbits.jopts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ArgumentParser<T> {
	
	private HashMap<String, ArgumentDefinition> argDefs = new HashMap<String, ArgumentDefinition>();
	
	private ArgumentDefinition defaultDef;
	
	public ArgumentParser(Class<T> type) throws InvalidArgumentDefinitionException {
		
		// List methods
		for (Method m: type.getMethods()){
			processMethod(m);
		}
	}
	
	private void processMethod(Method m) throws InvalidArgumentDefinitionException {
		
		for (Annotation a: m.getAnnotations()) {
			if (a instanceof Argument) {
				try {
					ArgumentDefinition def = new ArgumentDefinition(m, (Argument)a);
					
					if (argDefs.containsKey(def.getName())) {
						throw new IllegalStateException("Duplicated argument definition.");
					} else {
						argDefs.put(def.getName(), def);
					}
				} catch (Exception e) {
					throw new InvalidArgumentDefinitionException(((Argument) a).name(), 
							String.format("Invalid argument definition found in the declaration of %1$s.%2$s().",
									m.getDeclaringClass().getName(),
									m.getName()));
				}
			}
		}
	}
	
	
	public void process(String args[], T instance) throws IllegalArgumentException {
		int i;
		String arg;
		String val;
		ArgumentDefinition def;
		
		i = 0;
		while (i < args.length) {
			// Fetch the first part
			arg = args[i];
			i++;
			// Look for the parameter
			def = argDefs.get(arg);
			if (def != null) {
				
			
			
			} else {
				// Default value
				def = argDefs.get("");
				
			}
		}
	}
	
	
	private void invokeDefault(ArgumentDefinition def){
		
	}
	
	
}

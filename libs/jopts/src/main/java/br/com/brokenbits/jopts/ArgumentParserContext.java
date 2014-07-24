package br.com.brokenbits.jopts;

import java.util.HashSet;

public class ArgumentParserContext {
	
	private HashSet<String> used = new HashSet<String>();

	public void validateUsage(ArgumentDefinition def) throws ArgumentParserException {
		String name;
		
		// Get the parameter name
		name = def.getName();
		// If
		if (def.isUnique()) {
			if (used.contains(name)) {
				throw new DuplicatedArgumentException(def.getName());
			}
		}
		
		// Register the use
		used.add(name);
	}
	
	public boolean isUsed(ArgumentDefinition def) {
		return isUsed(def.getName());
	}
	
	public boolean isUsed(String name) {
		return used.contains(name);
	}
}

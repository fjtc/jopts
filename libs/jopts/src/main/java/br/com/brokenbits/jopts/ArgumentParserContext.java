package br.com.brokenbits.jopts;

import java.util.HashMap;

class ArgumentParserContext {
	
	private HashMap<String, String> used = new HashMap<String, String>();

	public void validateUnique(ArgumentDefinition def) throws DuplicatedArgumentException {
		String name;
		
		// Get the parameter name
		if (def.isUnique()) {
			name = used.get(def.getUniqueKey());
			if (name != null) {
				throw new DuplicatedArgumentException(name, def.getName());
			}
		}
		// Register the use
		used.put(def.getUniqueKey(), def.getName());
	}
}

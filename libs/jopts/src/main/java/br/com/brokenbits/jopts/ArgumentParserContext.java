package br.com.brokenbits.jopts;

import java.util.HashMap;

/**
 * This class is used to store the argument parser context. 
 * 
 * @author FJTC
 */
class ArgumentParserContext {
	
	private HashMap<String, ArgumentDefinition> used = new HashMap<String, ArgumentDefinition>();
	
	private String args[];
	
	private int current;
	
	private int last;

	ArgumentParserContext(String args[], int current, int count) {
		this.args = args;
		this.current = current;
		this.last = current + count;
	}
	
	public boolean hasNext(){
		return (current < last);
	}
	
	public String getNext() {
		String ret;
		
		if (hasNext()) {
			ret = args[current];
			current++;
		} else {
			ret = null;
		}
		return ret;
	}
	
	
	public void validateUnique(ArgumentDefinition def) throws DuplicatedArgumentException {
		ArgumentDefinition dup;
		
		// Get the parameter name
		if (def.isUnique()) {
			dup = used.get(def.getUniqueKey());
			if (dup != null) {
				throw new DuplicatedArgumentException(def.getName(), dup.getName());
			}
		}
		// Register the use
		used.put(def.getUniqueKey(), def);
	}
}

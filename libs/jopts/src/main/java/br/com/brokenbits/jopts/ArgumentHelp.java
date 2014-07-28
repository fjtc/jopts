package br.com.brokenbits.jopts;

/**
 * This class is used to store help information about the arguments.
 * 
 * @author FJTC
 */
public class ArgumentHelp implements Comparable<ArgumentHelp>{
	
	private String name;
	
	private String description;
	
	private Class<?> type;
	
	private String[] conflicts;

	/**
	 * Returns the name of the argument. It may be null if this describes the unnamed argument.
	 * 
	 * @return The name of the option.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the argument.
	 * 
	 * @param name The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the argument.
	 * 
	 * @return The description of the argument.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the argument.
	 * 
	 * @return The new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the type of the parameter of the argument. It may return null if the argument has no parameters.
	 * 
	 * @return The type of the parameter of the argument.
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Sets the type of the parameter of the argument. 
	 * 
	 * @param type The new type.
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Returns a list of parameters that conflicts to this parameter. It may return null if the 
	 * argument has no conflicts. It is important to notice that this list always includes the
	 * argument itself.
	 * 
	 * @return The list of conflicts or null if this argument has no conflicts.
	 */
	public String[] getConflicts() {
		return conflicts;
	}

	public void setConflicts(String[] conflicts) {
		this.conflicts = conflicts;
	}

	/**
	 * Compares this instance to another.
	 * 
	 * @param o The other instance.
	 * @return True if the name is the same or false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof ArgumentHelp) {
			return compareTo((ArgumentHelp)obj) == 0;
		} else {
			return false;
		}
	}

	/**
	 * Compares this instance to another. The comparison considers only the name 
	 * using a case sensitive lexicographic comparison and threats null as the 
	 * the smallest value possible.
	 * 
	 * @param o The other instance.
	 * @return Zero if the instances are the same, a negative value if this comes before o or
	 * a positive value if o comes before this.
	 */
	@Override
	public int compareTo(ArgumentHelp o) {
		
		if (this.name == null) {
			if ((o.name == null)) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if ((o.name == null)) {
				return 1;
			} else {
				return this.name.compareTo(o.name);
			}
		}
	}
}

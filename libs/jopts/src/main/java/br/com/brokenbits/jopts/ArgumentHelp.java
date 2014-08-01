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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to store help information about the arguments.
 * 
 * @author FJTC
 */
public class ArgumentHelp implements Comparable<ArgumentHelp>{
	
	/**
	 * The name of the argument.
	 */
	private String name;
	
	/**
	 * The description of the argument.
	 */
	private String description;
	
	/**
	 * The type of parameter of the argument.
	 */
	private ArgumentParameterType type;
	
	/**
	 * List of conflicts of this argument.
	 */
	private Set<String> conflicts;

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
	public ArgumentParameterType getType() {
		return type;
	}

	/**
	 * Sets the type of the parameter of the argument. 
	 * 
	 * @param type The new type.
	 */
	public void setType(ArgumentParameterType type) {
		this.type = type;
	}

	/**
	 * Returns a list of parameters that conflicts to this parameter. It may return null if the 
	 * argument has no conflicts. It is important to notice that this list always includes the
	 * argument itself.
	 * 
	 * @return The list of conflicts or null if this argument has no conflicts.
	 * @note Do not modify the contents of this set.
	 */
	public Set<String> getConflicts() {
		return conflicts;
	}

	/**
	 * Sets the list of conflicts.
	 * 
	 * @param conflicts List of conflicting options.
	 */
	public void setConflicts(Collection<String> conflicts) {
		this.conflicts = new HashSet<String>(conflicts);
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

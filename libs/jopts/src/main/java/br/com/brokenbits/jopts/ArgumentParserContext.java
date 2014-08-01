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

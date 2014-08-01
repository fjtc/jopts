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

/**
 * This is the base class for all JOPTS exceptions.
 * 
 * @author fjtc
 */
public class ArgumentParserException extends Exception {
	
	/**
	 * Serialization version number.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of the argument.
	 */
	private String name;

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param name The name of the argument.
	 */
	public ArgumentParserException(String name) {
		this.name = name;
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param name The name of the argument.
	 * @param message The associated message.
	 */
	public ArgumentParserException(String name, String message) {
		super(message);
		this.name = name;
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param name The name of the argument.
	 * @param cause The cause of this exception.
	 */
	public ArgumentParserException(String name, Throwable cause) {
		super(cause);
		this.name = name;
	}

	/**
	 * Creates a new instance of this class.
	 * 
	 * @param name The name of the argument.
	 * @param message The associated message.
	 * @param cause The cause of this exception.
	 */
	public ArgumentParserException(String name, String message, Throwable cause) {
		super(message, cause);
		this.name = name;
	}
	
	/**
	 * Returns the name of the affected argument.
	 *  
	 * @return The name of the argument or null if it is not available.
	 */
	public String getName() {
		return name;
	}
}

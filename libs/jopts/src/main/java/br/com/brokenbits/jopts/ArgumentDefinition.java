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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This class implements the parameter definition read from an annotated class.
 * 
 * @author fchino
 */
class ArgumentDefinition implements Cloneable {

	private String name;
	
	private String description;
	
	private String resourceName;
	
	private ArgumentParameterType type = ArgumentParameterType.NONE;
	
	private Method method;
	
	private String uniqueKey;

	public ArgumentDefinition(Method m, Argument a) {
		
		// Copy data from annotation
		if (a.name() == null) {
			throw new IllegalArgumentException("The argument name cannot be null.");
		}
		setName(a.name());
		setDescription(a.description());
		setResourceName(a.resourceName());
		setUniqueKey(a.uniqueKey());
		
		// Set the method
		setMethod(m);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ArgumentParameterType getType() {
		return type;
	}
	
	public boolean hasParameter(){
		return (type != ArgumentParameterType.NONE);
	}
	
	public Method getMethod() {
		return method;
	}

	public String getResourceName() {
		return resourceName;
	}
	
	public String getUniqueKey() {
		return uniqueKey;	
	}
	
	public boolean isUnique(){
		return (uniqueKey != null);
	}
	
	/**
	 * This method is used to 
	 * 
	 * @param s
	 * @return
	 */
	private String emptyIsNull(String s) {
		
		if ((s != null) && (s.isEmpty())) {
			s = null;
		}
		return s;		
	}

	private void setName(String name) {
		this.name = emptyIsNull(name);
	}

	private void setDescription(String description) {
		this.description = emptyIsNull(description);
	}

	private void setResourceName(String resourceName) {
		this.resourceName = emptyIsNull(resourceName);
	}
	
	private void setUniqueKey(String uniqueKey) {
		this.uniqueKey = emptyIsNull(uniqueKey);
	}
	
	private void setType(Class<?> type) {

		this.type = ArgumentParameterType.forClass(type);
		if (this.type == null) {
			throw new IllegalArgumentException("The target method parameter type is not supported.");
		}
	}

	private void setMethod(Method m) {
		
		// Validate the method return
		if (!m.getReturnType().equals(Void.TYPE)) {
			throw new IllegalArgumentException("The target method must return null.");
		}
		
		// Validate the method modifier
		if ((m.getModifiers() & Modifier.STATIC) != 0) {
			throw new IllegalArgumentException("The target method cannot be static.");
		}
		
		// Check the parameters
		Class<?> params[] = m.getParameterTypes();
		if (isUnnamed()) {
			// Default argument
			if (params.length != 1) {
				throw new IllegalArgumentException("The unnamed setter must have one parameter.");
			}
			if (!params[0].equals(String.class)) {
				throw new IllegalArgumentException("The unnamed setter parameter must be a string.");
			}
			setType(params[0]);					
		} else {
			// Validate the argument type
			if (params.length > 1) {
				throw new IllegalArgumentException("The target method cannot have more than one parameter.");			
			} else if (params.length == 1) {
				setType(params[0]);
			}
		}

		// Set the method
		this.method = m;
	}

	/**
	 * Verifies if this parameter definition represents the unnamed setter.
	 * 
	 * @return true if it is the unnamed setter or false otherwise.
	 */
	boolean isUnnamed(){
		return (this.name == null);
	}
	
	/**
	 * Invokes the method.
	 * 
	 * @param target The target instance.
	 * @param paramValue The parameter or null if it is not present.
	 * @throws IllegalArgumentException It one of the parameters is invalid.
	 * @throws br.com.brokenbits.jopts.InvalidParameterTypeException It the paramValue cannot
	 * be converted to the required type.
	 * @throws br.com.brokenbits.jopts.ArgumentParserException If the setter could not be invoked 
	 * due to errors.
	 */
	public void invokeMethod(Object target, String paramValue) throws IllegalArgumentException, ArgumentParserException, InvalidParameterTypeException {
		try { 
			if (this.hasParameter()) {
				if (isUnnamed()) {
					this.method.invoke(target, paramValue);
				} else {
					Object param;
					
					// Validate the parameter type
					try {
						param = this.getType().parse(paramValue);
					} catch (IllegalArgumentException e) {
						throw new InvalidParameterTypeException(this.name, "Invalid value type.");
					}
					
					// Invoke...
					this.method.invoke(target, param);
				}
			} else {
				// No parameter
				if (paramValue != null) {
					throw new IllegalArgumentException(String.format("The argument %1$s has no parameter.", this.name));
				}
				// Invoke...
				this.method.invoke(target);
			}
		} catch (IllegalAccessException e) {
			throw new InvalidArgumentDefinitionException(this.name, "Unable to invoke the setter.", e);
		} catch (InvocationTargetException e) {
			throw new InvalidArgumentDefinitionException(this.name, "Unable to invoke the setter.", e);
		}
	}
}

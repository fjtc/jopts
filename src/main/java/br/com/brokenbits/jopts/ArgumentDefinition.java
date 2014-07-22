package br.com.brokenbits.jopts;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class ArgumentDefinition implements Cloneable {

	private String name;
	
	private String description;
	
	private String resourceName;
	
	private Class<?> type;
	
	private Method method;
	
	private String uniqueKey;

	public ArgumentDefinition(Method m, Argument a) {
		
		// Copy data from annotation
		if (a.name() == null) {
			throw new IllegalArgumentException("The argument name cannot be null.");
		}
		this.name = a.name();
		this.description = a.description();
		this.resourceName = a.resourceName();
		
		// Unique key
		if ((a.uniqueKey() != null) && (!a.uniqueKey().isEmpty())) {
			this.uniqueKey = a.uniqueKey();
		}
		
		// Set the method
		this.method = m;
		
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
		if (this.name == null) {
			// Default argument
			if ((params.length == 1) && (params[0].equals(String.class))) {
				this.type = params[0];
			} else {
				throw new IllegalArgumentException("The default parameter contain one string parameter.");
			}
		} else {
			// Validate the argument type
			if (params.length > 1) {
				throw new IllegalArgumentException("The target method cannot have more than one parameter.");			
			} else if (params.length == 1) {
				if (isValidType(params[0])) {
					this.type = params[0]; 
				} else {
					throw new IllegalArgumentException("The target method parameter type is not supported.");
				}
			}
		}
	}
	
	private boolean isValidType(Class<?> type) {
		
		return 
				type.equals(Byte.TYPE) || type.equals(Byte.class) ||
				type.equals(Short.TYPE) || type.equals(Short.class) ||
				type.equals(Integer.TYPE) || type.equals(Integer.class) ||
				type.equals(Long.class) || type.equals(Long.TYPE) ||
				type.equals(Float.class) || type.equals(Float.TYPE) ||
				type.equals(Double.class) || type.equals(Double.TYPE) ||
				type.equals(String.class);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean hasParameter(){
		return (type == null);
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
}

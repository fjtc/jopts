package br.com.brokenbits.jopts;

/**
 * This enumeration is used to indicate the type of a given argument parameter.
 * 
 * @author FJTC
 */
public enum ArgumentParameterType {
	/**
	 * No parameter.
	 */
	NONE(null),
	/**
	 * Long value.
	 */
	LONG(Long.class),
	/**
	 * Double value.
	 */
	DOUBLE(Double.class),
	/**
	 * String value.
	 */
	STRING(String.class);
	
	private Class<?> typeClass;
	
	private ArgumentParameterType(Class<?> typeClass) {
		this.typeClass = typeClass;
	}
	
	/**
	 * Returns the class the represents this argument parameter type.
	 * 
	 * @return The class of the type or null if not class is associated with this type.
	 */
	public Class<?> getTypeClass(){
		return this.typeClass;
	}
	
	/**
	 * Parses a string and converts it into a instance of the class. 
	 * 
	 * @param s The string that represents this parameter or null for none.
	 * @return The instance that represents the parameter.
	 * @throws IllegalArgumentException If s contains an invalid value.
	 */
	public Object parse(String s) throws IllegalArgumentException {
		Object v = null;
		
		// Test for null argument
		if (this == NONE) {
			if (s != null) {
				throw new IllegalArgumentException("For NONE, s must be null.");
			}
		} else {
			if (s == null) {
				throw new IllegalArgumentException("s cannot be null.");
			}
		}
		
		// Parse me!
		switch (this) {
		case NONE:
			break;
		case LONG:
			try {
				v = new Long(Long.parseLong(s));
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format("'%1$s' is not a valid long value.", s));
			}
			break;
		case DOUBLE:
			try {
				v = new Double(Double.parseDouble(s));
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format("'%1$s' is not a valid double value.", s));
			}
			break;
		case STRING:
			v = s;
			break;
		}
		return v;
	}
	
	/**
	 * Returns the enumeration that represents a given argument parameter type.
	 * 
	 * @param c The class that represents the parameter.
	 * @return The enumeration of the type or null for incompatible type.
	 */
	public static ArgumentParameterType forClass(Class<?> c){
		
		if (c == null) {
			return NONE;
		} else if (Long.class.equals(c) || Long.TYPE.equals(c)) {
			return LONG;
		} else if (Double.class.equals(c) || Double.TYPE.equals(c)) {
			return DOUBLE;
		} else if (String.class.equals(c)) {
			return STRING;
		} else {
			return null;
		}
	}
}

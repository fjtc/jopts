package br.com.brokenbits.jopts;

/**
 * 
 * 
 * @author FJTC
 */
public class InvalidParameterTypeException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParameterTypeException(String name) {
		super(name);
	}

	public InvalidParameterTypeException(String name, String message) {
		super(name, message);
	}

	public InvalidParameterTypeException(String name, Throwable cause) {
		super(name, cause);
	}

	public InvalidParameterTypeException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
	}

	public InvalidParameterTypeException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
	}
}

package br.com.brokenbits.jopts;

/**
 * This exception is used to indicate when the parameter definition is incorrect. 
 * 
 * @author FJTC
 */
public class InvalidArgumentDefinitionException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidArgumentDefinitionException(String name) {
		super(name);
	}

	public InvalidArgumentDefinitionException(String name, String message) {
		super(name, message);
	}

	public InvalidArgumentDefinitionException(String name, Throwable cause) {
		super(name, cause);
	}

	public InvalidArgumentDefinitionException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
	}

	public InvalidArgumentDefinitionException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
	}
}

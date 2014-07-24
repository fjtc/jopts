package br.com.brokenbits.jopts;

public class InvalidParameterTypeException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParameterTypeException(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterTypeException(String name, String message) {
		super(name, message);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterTypeException(String name, Throwable cause) {
		super(name, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterTypeException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterTypeException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

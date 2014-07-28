package br.com.brokenbits.jopts;

public class MissingArgumentParameterException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingArgumentParameterException(String name) {
		super(name);
	}

	public MissingArgumentParameterException(String name, String message) {
		super(name, message);
	}

	public MissingArgumentParameterException(String name, Throwable cause) {
		super(name, cause);
	}

	public MissingArgumentParameterException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
	}

	public MissingArgumentParameterException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
	}
}

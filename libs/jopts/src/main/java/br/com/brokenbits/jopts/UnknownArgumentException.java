package br.com.brokenbits.jopts;

public class UnknownArgumentException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownArgumentException(String name) {
		super(name);
	}

	public UnknownArgumentException(String name, String message) {
		super(name, message);
	}

	public UnknownArgumentException(String name, Throwable cause) {
		super(name, cause);
	}

	public UnknownArgumentException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
	}

	public UnknownArgumentException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
	}
}

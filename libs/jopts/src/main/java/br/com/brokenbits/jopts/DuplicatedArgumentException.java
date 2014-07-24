package br.com.brokenbits.jopts;

public class DuplicatedArgumentException extends ArgumentParserException {

	public DuplicatedArgumentException(String name) {
		super(name);
	}

	public DuplicatedArgumentException(String name, String message) {
		super(name, message);
	}

	public DuplicatedArgumentException(String name, Throwable cause) {
		super(name, cause);
	}

	public DuplicatedArgumentException(String name, String message,
			Throwable cause) {
		super(name, message, cause);
	}

	public DuplicatedArgumentException(String name, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, message, cause, enableSuppression, writableStackTrace);
	}
}

package br.com.brokenbits.jopts;

public class ArgumentParserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	public ArgumentParserException(String name) {
		this.name = name;
	}

	public ArgumentParserException(String name, String message) {
		super(message);
		this.name = name;
	}

	public ArgumentParserException(String name, Throwable cause) {
		super(cause);
		this.name = name;
	}

	public ArgumentParserException(String name, String message, Throwable cause) {
		super(message, cause);
		this.name = name;
	}

	public ArgumentParserException(String name, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

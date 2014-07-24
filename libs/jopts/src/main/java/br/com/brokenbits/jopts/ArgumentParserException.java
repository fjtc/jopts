package br.com.brokenbits.jopts;

/**
 * This is the base class for all JOPTS exceptions.
 * 
 * @author fjtc
 */
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
	
	/**
	 * Returns the name of the affected parameter.
	 *  
	 * @return The name of the parameter or null if it is not available.
	 */
	public String getName() {
		return name;
	}
}

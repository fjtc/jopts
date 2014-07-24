package br.com.brokenbits.jopts;

/**
 * This exception is used to notify that one parameter marked as unique is
 * used more than once in the same command line.
 * 
 * @author FJTC
 */
public class DuplicatedArgumentException extends ArgumentParserException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String duplicated;

	public DuplicatedArgumentException(String name, String duplicated) {
		super(name);
		this.duplicated = duplicated;
	}

	public DuplicatedArgumentException(String name, String duplicated, Throwable cause) {
		super(name, cause);
		this.duplicated = duplicated;
	}

	public DuplicatedArgumentException(String name, String duplicated,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(name, null, cause, enableSuppression, writableStackTrace);
		this.duplicated = duplicated;
	}
	
	public String getDuplicated(){
		return duplicated;
	}
}

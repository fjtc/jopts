package br.com.brokenbits.jopts;

public class Sample1 {
	
	private String string;
	
	private int integer;

	public Sample1() {
	}

	public String getString() {
		return string;
	}

	@Argument(name="string")
	public void setString(String string) {
		this.string = string;
	}

	public int getInteger() {
		return integer;
	}

	@Argument(name="integer")
	public void setInteger(int integer) {
		this.integer = integer;
	}
	
	

}

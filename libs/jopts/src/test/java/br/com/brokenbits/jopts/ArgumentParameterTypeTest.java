package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

import javax.print.DocFlavor;

import org.junit.Test;

public class ArgumentParameterTypeTest {

	@Test
	public void testGetTypeClass() {
	
		assertNull(ArgumentParameterType.NONE.getTypeClass());
		assertEquals(Long.class, ArgumentParameterType.LONG.getTypeClass());
		assertEquals(Double.class, ArgumentParameterType.DOUBLE.getTypeClass());
		assertEquals(String.class, ArgumentParameterType.STRING.getTypeClass());
	}

	@Test
	public void testParseNone() {
		
		assertNull(ArgumentParameterType.NONE.parse(null));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseNoneFail() {
		
		assertNull(ArgumentParameterType.NONE.parse(""));
	}
	
	@Test
	public void testParseLong() {
		Object v;
		
		for (String s: new String[]{"0", "-1", "2", "1231435124"}) {
			v = ArgumentParameterType.LONG.parse(s);
			assertTrue(v instanceof Long);
			assertEquals(new Long(Long.parseLong(s)), (Long)v);
		}
	}
	
	@Test
	public void testParseLongFail() {
		
		for (String s: new String[]{null, "", "a", "2.1"}) {
			try {
				ArgumentParameterType.LONG.parse(s);
				fail();
			} catch (IllegalArgumentException e) {}
		}
	}	

	@Test
	public void testParseDouble() {
		Object v;
		
		for (String s: new String[]{"0", "-1", "2", "1231435124"}) {
			v = ArgumentParameterType.DOUBLE.parse(s);
			assertTrue(v instanceof Double);
			assertEquals(new Double(Double.parseDouble(s)), (Double)v);
		}
	}
	
	@Test
	public void testParseDoubleFail() {
		
		for (String s: new String[]{null, "", "a"}) {
			try {
				ArgumentParameterType.DOUBLE.parse(s);
				fail();
			} catch (IllegalArgumentException e) {}
		}
	}
	
	@Test
	public void testParseString() {
		Object v;
		
		for (String s: new String[]{"0", "-1", "2", "1231435124"}) {
			v = ArgumentParameterType.STRING.parse(s);
			assertTrue(v instanceof String);
			assertEquals(s, v);
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testParseStringFail() {
		
		ArgumentParameterType.DOUBLE.parse(null);
	}	

	@Test
	public void testForClass() {
		
		// Valid types
		assertEquals(ArgumentParameterType.NONE, ArgumentParameterType.forClass(null));
		assertEquals(ArgumentParameterType.LONG, ArgumentParameterType.forClass(Long.class));
		assertEquals(ArgumentParameterType.LONG, ArgumentParameterType.forClass(Long.TYPE));
		assertEquals(ArgumentParameterType.DOUBLE, ArgumentParameterType.forClass(Double.class));
		assertEquals(ArgumentParameterType.DOUBLE, ArgumentParameterType.forClass(Double.TYPE));
		assertEquals(ArgumentParameterType.STRING, ArgumentParameterType.forClass(String.class));
		
		// Unknown types
		assertNull(ArgumentParameterType.forClass(Object.class));
	}

}

/*
 * Copyright (c) 2014, Fabio Jun Takada Chino
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *   
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

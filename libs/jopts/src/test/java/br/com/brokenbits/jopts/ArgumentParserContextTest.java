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

import java.lang.reflect.Method;

import org.junit.Test;

public class ArgumentParserContextTest {
	
	private static class Sample {

		@Argument(name="uniqueOption", uniqueKey="unique")
		public void setUnique(){
		}
		
		@Argument(name="uniqueOption2", uniqueKey="unique")
		public void setUnique2(){
		}
		
		@Argument(name="nonUniqueOption")
		public void setNonUnique(){
		}
	}
	
	private ArgumentDefinition getInstance(String name) throws NoSuchMethodException {
		Method m;
		Argument a;
				
		m = Sample.class.getMethod(name);
		
		return new ArgumentDefinition(m, m.getAnnotation(Argument.class));
	}
	
	
	private static final String ARGS[] = {"0", "1", "2", "3"};
	
	@Test
	public void testArgumentParserContext() {
		ArgumentParserContext c;
		
		c = new ArgumentParserContext(ARGS, 0, ARGS.length);
		assertNotNull(c);

		c = new ArgumentParserContext(ARGS, 1, 2);
		assertNotNull(c);
	}	

	@Test
	public void testNext() {
		ArgumentParserContext c;
		
		c = new ArgumentParserContext(ARGS, 0, ARGS.length);
		assertNotNull(c);
		
		assertTrue(c.hasNext());
		assertEquals("0", c.getNext());
		assertTrue(c.hasNext());
		assertEquals("1", c.getNext());
		assertTrue(c.hasNext());
		assertEquals("2", c.getNext());
		assertTrue(c.hasNext());
		assertEquals("3", c.getNext());
		assertFalse(c.hasNext());
		assertNull(c.getNext());
		
		c = new ArgumentParserContext(ARGS, 1, 2);
		assertNotNull(c);
		
		assertTrue(c.hasNext());
		assertEquals("1", c.getNext());
		assertTrue(c.hasNext());
		assertEquals("2", c.getNext());
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}

	@Test
	public void testValidateUnique() throws Exception {
		ArgumentDefinition a1;
		ArgumentDefinition a2;
		ArgumentDefinition a3;
		ArgumentParserContext c;
		
		a1 = getInstance("setNonUnique");
		a2 = getInstance("setUnique");
		a3 = getInstance("setUnique2");
		
		c = new ArgumentParserContext(ARGS, 0, ARGS.length);
		
		c.validateUnique(a1);
		c.validateUnique(a1);
		c.validateUnique(a1);
		
		c.validateUnique(a2);
		try {
			c.validateUnique(a2);
			fail();
		} catch (DuplicatedArgumentException e) {}
		
		try {
			c.validateUnique(a3);
			fail();
		} catch (DuplicatedArgumentException e) {}
	}
}

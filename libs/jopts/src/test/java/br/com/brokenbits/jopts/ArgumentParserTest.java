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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Test;

public class ArgumentParserTest {
	
	private static class Sample {
		
		private String string;
		
		private String string2;
		
		private Long longVal;
		
		private Double doubleVal;
		
		private Boolean boolVal;

		public String getString() {
			return string;
		}

		@Argument(name="string")
		public void setString(String string) {
			this.string = string;
		}

		@Arguments(arguments={
				@Argument(name="string2"), 
				@Argument(name="str2")})
		public void setString2(String string) {
			this.string2 = string;
		}

		public String getString2() {
			return string2;
		}
		
		public Long getLong() {
			return longVal;
		}

		@Argument(name="long")
		public void setLong(Long l) {
			this.longVal = l;
		}
		
		@Argument(name="long2")
		public void setLong2(long l) {
			this.longVal = l;
		}

		public Boolean getBool() {
			return boolVal;
		}

		@Argument(name="bool")
		public void setBool() {
			this.boolVal = true;
		}
		
		public Double getDouble() {
			return this.doubleVal;
		}

		@Argument(name="double")
		public void setDouble(double d) {
			this.doubleVal = d;
		}

		@Argument(name="double2")
		public void setDouble2(Double d) {
			this.doubleVal = d;
		}		
	}
	
	private static class Duplicated {

		@Argument(name="double")
		public void setDouble(double d) {
		}

		@Argument(name="double")
		public void setDouble2(double d) {
		}
	}
	
	private static class Unnamed {
		
		private ArrayList<String> list = new ArrayList<String>();

		@Argument()
		public void addString(String s) {
			list.add(s);
		}
		
		public List<String> getList(){
			return this.list;
		}
	}
	
	private static class UnnamedUnique {
		
		@Argument(uniqueKey="a")
		public void addString(String s) {
		}
	}	
	
	private static class Duplicated2 {

		@Argument()
		public void setDouble(double d) {
		}

		@Argument()
		public void setDouble2(double d) {
		}
	}	

	private static class Help {

		
		@Argument(description="unnamed description.", resourceName="unnamed")
		public void setDefault(String s){
		}
		
		@Argument(name="unique", uniqueKey="unique", description="unique description.", resourceName="unique")
		public void setUnique(long l) {
		}
		
		@Argument(name="arg", description="arg description.", resourceName="arg")
		public void setArg(Long l) {
		}		

		@Argument(name="command1", uniqueKey="command", description="command1 description.", resourceName="command1")
		public void setCommand1(double d){
		}
		
		@Argument(name="command2", uniqueKey="command", description="command2 description.", resourceName="command2")
		public void setCommand2(Double d){
		}
		
		@Argument(name="zero", description="zero description.", resourceName="zero")
		public void setZero(){
		}			
	}
	
	
	@Test
	public void testArgumentParser() throws Exception {
		ArgumentParser<Sample> parser;
		
		parser = new ArgumentParser<Sample>(Sample.class);
		assertNotNull(parser);
	}

	
	@Test(expected=InvalidArgumentDefinitionException.class)
	public void testArgumentParserFailDuplicated() throws Exception {
		ArgumentParser<Duplicated> parser;
		
		parser = new ArgumentParser<Duplicated>(Duplicated.class);
		assertNotNull(parser);
	}

	@Test(expected=InvalidArgumentDefinitionException.class)
	public void testArgumentParserFailDuplicated2() throws Exception {
		ArgumentParser<Duplicated2> parser;
		
		parser = new ArgumentParser<Duplicated2>(Duplicated2.class);
		assertNotNull(parser);
	}
	
	
	@Test
	public void testParse() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		assertNotNull(parser);
		
		parser.process(new String[]{"bool", "string", "s", "string2", "s2", "long", "12345", "double", "1.23"}, s);
		assertNotNull(s.getBool());
		assertTrue(s.getBool());
		assertEquals("s", s.getString());
		assertEquals(new Long(12345), s.getLong());
		assertEquals(new Double(1.23), s.getDouble());
	}
	
	@Test
	public void testParseFailUnknown() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		
		try {
			parser.process(new String[]{"qwerty"}, s);
			fail();
		} catch (UnknownArgumentException e) {
			assertEquals("qwerty", e.getName());
		}		
	}
	
	@Test
	public void testAliases() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s1 = new Sample();
		Sample s2 = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		
		parser.process(new String[]{"string2", "a"}, s1);
		parser.process(new String[]{"str2", "a"}, s2);
		assertEquals(s1.getString2(), s2.getString2());
	}	
	
	
	@Test
	public void testParseFailMissing() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		assertNotNull(parser);
		
		try {
			parser.process(new String[]{"string"}, s);
			fail();
		} catch (MissingArgumentParameterException e) {
			assertEquals("string", e.getName());
		}
	}

	@Test
	public void testParseInvalidLong() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		assertNotNull(parser);
		
		try {
			parser.process(new String[]{"long", "a"}, s);
			fail();
		} catch (InvalidParameterTypeException e) {
			assertEquals("long", e.getName());
		}
	}
	
	@Test
	public void testParseInvalidDouble() throws Exception {
		ArgumentParser<Sample> parser;
		Sample s = new Sample();
		
		parser = new ArgumentParser<Sample>(Sample.class);
		assertNotNull(parser);
		
		try {
			parser.process(new String[]{"double", "a"}, s);
			fail();
		} catch (InvalidParameterTypeException e) {
			assertEquals("double", e.getName());
		}
	}
	
	
	@Test
	public void testParseUnnamed() throws Exception {
		ArgumentParser<Unnamed> parser;
		Unnamed s = new Unnamed();
		
		parser = new ArgumentParser<Unnamed>(Unnamed.class);
		parser.process(new String[]{"1", "2", "3"}, s);
		assertNotNull(parser);
		assertEquals(3, s.getList().size());
		assertEquals("1", s.getList().get(0));
		assertEquals("2", s.getList().get(1));
		assertEquals("3", s.getList().get(2));
	}		
	
	@Test
	public void testParseDuplicatedUnnamed() throws Exception {
		ArgumentParser<UnnamedUnique> parser;
		UnnamedUnique s = new UnnamedUnique();
		
		parser = new ArgumentParser<UnnamedUnique>(UnnamedUnique.class);
		assertNotNull(parser);
		try {
			parser.process(new String[]{"double", "a"}, s);
			fail();
		} catch (DuplicatedArgumentException e) {
			assertNull(e.getName());
		}
	}	
	
	@Test
	public void testHelp() throws Exception {
		ArgumentParser<Help> parser;
		List<ArgumentHelp> l;
		ArgumentHelp h;
		
		parser = new ArgumentParser<Help>(Help.class);
		l = parser.generateHelp(null);
		
		assertEquals(6, l.size());
		
		// unnamed
		h = l.get(0);
		assertNull(h.getName());
		assertEquals(ArgumentParameterType.STRING, h.getType());
		assertEquals("unnamed description.", h.getDescription());
		assertNull(h.getConflicts());
		
		// arg
		h = l.get(1);
		assertEquals("arg", h.getName());
		assertEquals(ArgumentParameterType.LONG, h.getType());
		assertEquals("arg description.", h.getDescription());
		assertNull(h.getConflicts());
		
		// command1
		h = l.get(2);
		assertEquals("command1", h.getName());
		assertEquals(ArgumentParameterType.DOUBLE, h.getType());
		assertEquals("command1 description.", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(2, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("command1"));
		assertTrue(h.getConflicts().contains("command2"));
		
		// command2
		h = l.get(3);
		assertEquals("command2", h.getName());
		assertEquals(ArgumentParameterType.DOUBLE, h.getType());
		assertEquals("command2 description.", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(2, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("command1"));
		assertTrue(h.getConflicts().contains("command2"));
		
		// unique
		h = l.get(4);
		assertEquals("unique", h.getName());
		assertEquals(ArgumentParameterType.LONG, h.getType());
		assertEquals("unique description.", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(1, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("unique"));
		
		// arg
		h = l.get(5);
		assertEquals("zero", h.getName());
		assertEquals(ArgumentParameterType.NONE, h.getType());
		assertEquals("zero description.", h.getDescription());
		assertNull(h.getConflicts());
	}
	
	@Test
	public void testHelpResource() throws Exception {
		ArgumentParser<Help> parser;
		List<ArgumentHelp> l;
		ArgumentHelp h;
		ResourceBundle res = ResourceBundle.getBundle("br.com.brokenbits.jopts.ArgumentParserTest");
		
		parser = new ArgumentParser<Help>(Help.class);
		l = parser.generateHelp(res);
		
		assertEquals(6, l.size());
		
		// unnamed
		h = l.get(0);
		assertNull(h.getName());
		assertEquals(ArgumentParameterType.STRING, h.getType());
		assertEquals("unnamed resource", h.getDescription());
		assertNull(h.getConflicts());
		
		// arg
		h = l.get(1);
		assertEquals("arg", h.getName());
		assertEquals(ArgumentParameterType.LONG, h.getType());
		assertEquals("arg description.", h.getDescription());
		assertNull(h.getConflicts());
		
		// command1
		h = l.get(2);
		assertEquals("command1", h.getName());
		assertEquals(ArgumentParameterType.DOUBLE, h.getType());
		assertEquals("command1 resource", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(2, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("command1"));
		assertTrue(h.getConflicts().contains("command2"));
		
		// command2
		h = l.get(3);
		assertEquals("command2", h.getName());
		assertEquals(ArgumentParameterType.DOUBLE, h.getType());
		assertEquals("command2 resource", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(2, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("command1"));
		assertTrue(h.getConflicts().contains("command2"));
		
		// unique
		h = l.get(4);
		assertEquals("unique", h.getName());
		assertEquals(ArgumentParameterType.LONG, h.getType());
		assertEquals("unique resource", h.getDescription());
		assertNotNull(h.getConflicts());
		assertEquals(1, h.getConflicts().size());
		assertTrue(h.getConflicts().contains("unique"));
		
		// arg
		h = l.get(5);
		assertEquals("zero", h.getName());
		assertEquals(ArgumentParameterType.NONE, h.getType());
		assertEquals("zero resource", h.getDescription());
		assertNull(h.getConflicts());
	}	
}

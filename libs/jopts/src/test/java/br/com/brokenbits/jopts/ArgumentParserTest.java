package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

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
			return string;
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
	}
	
	private static class Duplicated {

		@Argument(name="double")
		public void setDouble(double d) {
		}

		@Argument(name="double")
		public void setDouble2(double d) {
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
		
		parser.process(new String[]{"bool", "string", "s", "string2", "s2", "long", "12345"}, s);
		assertNotNull(s.getBool());
		assertTrue(s.getBool());
		assertEquals("s", s.getString());
		assertEquals(new Long(12345), s.getLong());
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
}

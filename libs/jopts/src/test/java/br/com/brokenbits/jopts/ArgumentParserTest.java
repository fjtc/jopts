package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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

		
		@Argument(description="setDefault", resourceName="setDefault")
		public void setDefault(){
		}
		
		@Argument(description="setUnique", resourceName="setUnique")
		public void setUnique(int i){
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
}

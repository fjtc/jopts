package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

public class ArgumentDefinitionTest {
	
	
	public static class Sample {
		
		private boolean marked;
		
		@Argument(name="marked")
		public void setMarked() {
			marked = true;
		}
		
		public boolean isMarked(){
			return marked;
		}		
		
		private String string;
		
		@Argument(name="string", description="description", resourceName="resource", uniqueKey="key")
		public void setString(String s) {
			string = s;
		}
		
		public String getString(){
			return string;
		}
		
		private Long longValue;

		public Long getLong() {
			return longValue;
		}

		@Argument(name="long", description="description", resourceName="resource", uniqueKey="key")
		public void setLong(long integer) {
			this.longValue = integer;
		}
		
		private Long longValue2;

		public Long getLong2() {
			return longValue2;
		}

		@Argument(name="long2", description="description", resourceName="resource", uniqueKey="key")
		public void setLong2(Long integer) {
			this.longValue2 = integer;
		}		
		
		private Double doubleValue;
		
		@Argument(name="double", description="description", resourceName="resource", uniqueKey="key")
		public void setDouble(double d) {
			doubleValue = d;
		}
		
		public Double getDouble() {
			return doubleValue;
		}
		
		private Double doubleValue2;
		
		@Argument(name="double2", description="description", resourceName="resource", uniqueKey="key")
		public void setDouble2(Double d) {
			doubleValue2 = d;
		}
		
		public Double getDouble2() {
			return doubleValue2;
		}
		
		
		private String unnamed;
		
		@Argument(name="", description="description", resourceName="resource", uniqueKey="key")
		public void setUnnamed(String s) {
			unnamed = s;
		}
		
		public String getUnnamed(){
			return unnamed;
		}		
		
		@Argument(name="test")
		public void setTest1() {
		}

		@Argument(name="test", description="description")
		public void setTest2() {
		}
		
		@Argument(name="test", description="description", resourceName="resource")
		public void setTest3() {
		}
		
		@Argument(name="test", description="description", resourceName="resource", uniqueKey="key")
		public void setTest4() {
		}
	}
	
	@Test
	public void testMark() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setMarked");
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("marked", d.getName());
		assertNull(d.getDescription());
		assertNull(d.getResourceName());
		assertNull(d.getUniqueKey());
		assertFalse(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.NONE, d.getType());
		assertEquals(m, d.getMethod());
		assertFalse(d.hasParameter());
		
		s = new Sample();
		assertFalse(s.isMarked());
		d.invokeMethod(s, null);
		assertTrue(s.isMarked());
	}
	
	@Test
	public void testSetString() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setString", String.class);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("string", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.STRING, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getString());
		d.invokeMethod(s, "value");
		assertEquals("value", s.getString());
	}
	
	@Test
	public void testSetLong() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setLong", Long.TYPE);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("long", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.LONG, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getLong());
		d.invokeMethod(s, "123456");
		assertEquals(new Long(123456), s.getLong());
	}	

	@Test
	public void testSetLong2() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setLong2", Long.class);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("long2", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.LONG, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getLong2());
		d.invokeMethod(s, "123456");
		assertEquals(new Long(123456), s.getLong2());
	}	
	
	@Test
	public void testSetDouble() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setDouble", Double.TYPE);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("double", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.DOUBLE, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getDouble());
		d.invokeMethod(s, "123456.7");
		assertEquals(new Double(123456.7), s.getDouble());
	}
	
	@Test
	public void testSetDouble2() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setDouble2", Double.class);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("double2", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.DOUBLE, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getDouble2());
		d.invokeMethod(s, "123456.7");
		assertEquals(new Double(123456.7), s.getDouble2());
	}
	
	@Test
	public void testSetUnnamed() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		Sample s;
		
		m = Sample.class.getMethod("setUnnamed", String.class);
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertNull(d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertTrue(d.isUnnamed());		
		assertEquals(ArgumentParameterType.STRING, d.getType());
		assertEquals(m, d.getMethod());
		assertTrue(d.hasParameter());
		
		s = new Sample();
		assertNull(s.getUnnamed());
		d.invokeMethod(s, "oops!");
		assertEquals("oops!", s.getUnnamed());
	}	
	
	@Test
	public void testTest1() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		
		m = Sample.class.getMethod("setTest1");
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("test", d.getName());
		assertNull(d.getDescription());
		assertNull(d.getResourceName());
		assertNull(d.getUniqueKey());
		assertFalse(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.NONE, d.getType());
		assertEquals(m, d.getMethod());
		assertFalse(d.hasParameter());
	}
	
	@Test
	public void testTest2() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		
		m = Sample.class.getMethod("setTest2");
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("test", d.getName());
		assertEquals("description", d.getDescription());
		assertNull(d.getResourceName());
		assertNull(d.getUniqueKey());
		assertFalse(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.NONE, d.getType());
		assertEquals(m, d.getMethod());
		assertFalse(d.hasParameter());
	}
	
	@Test
	public void testTest3() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		
		m = Sample.class.getMethod("setTest3");
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("test", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertNull(d.getUniqueKey());
		assertFalse(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.NONE, d.getType());
		assertEquals(m, d.getMethod());
		assertFalse(d.hasParameter());
	}	
	
	@Test
	public void testTest4() throws Exception {
		Method m;
		Argument a;
		ArgumentDefinition d;
		
		m = Sample.class.getMethod("setTest4");
		a = m.getAnnotation(Argument.class);
		
		d = new ArgumentDefinition(m, a);
		assertEquals("test", d.getName());
		assertEquals("description", d.getDescription());
		assertEquals("resource", d.getResourceName());
		assertEquals("key", d.getUniqueKey());
		assertTrue(d.isUnique());
		assertFalse(d.isUnnamed());		
		assertEquals(ArgumentParameterType.NONE, d.getType());
		assertEquals(m, d.getMethod());
		assertFalse(d.hasParameter());
	}		
}

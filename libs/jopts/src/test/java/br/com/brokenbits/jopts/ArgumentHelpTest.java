package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ArgumentHelpTest {

	@Test
	public void testGetSetName() {
		ArgumentHelp a;
		
		a = new ArgumentHelp();
		assertNull(a.getName());
		a.setName("name");
		assertEquals("name", a.getName());
	}


	@Test
	public void testGetSetDescription() {
		ArgumentHelp a;
		
		a = new ArgumentHelp();
		assertNull(a.getDescription());
		a.setDescription("description");
		assertEquals("description", a.getDescription());
	}

	@Test
	public void testGetSetType() {
		ArgumentHelp a;
		
		a = new ArgumentHelp();
		assertNull(a.getType());
		a.setType(String.class);
		assertEquals(String.class, a.getType());
	}

	@Test
	public void testGetSetConflicts() {
		ArgumentHelp a;
		ArrayList<String> l;
		
		a = new ArgumentHelp();
		
		assertNull(a.getConflicts());
		
		l = new ArrayList<String>();
		l.add("a1");
		l.add("a2");
		
		a.setConflicts(l);
		assertNotNull(a.getConflicts());
		assertEquals(2, a.getConflicts().size());
		assertTrue(a.getConflicts().contains("a1"));
		assertTrue(a.getConflicts().contains("a2"));
	}


	@Test
	public void testEqualsObject() {
		ArgumentHelp a1;
		ArgumentHelp a2;
		
		a1 = new ArgumentHelp();
		a2 = new ArgumentHelp();
		
		assertTrue(a1.equals(a2));
		assertTrue(a2.equals(a1));
		
		a1.setName("a");
		a2.setName("a");
		assertTrue(a1.equals(a2));
		assertTrue(a2.equals(a1));
		
		a1.setName("a");
		a2.setName(null);
		assertFalse(a1.equals(a2));
		assertFalse(a2.equals(a1));
		
		a1.setName("a");
		a2.setName("b");
		assertFalse(a1.equals(a2));
		assertFalse(a2.equals(a1));	
	}

	@Test
	public void testCompareTo() {
		ArgumentHelp a1;
		ArgumentHelp a2;
		
		a1 = new ArgumentHelp();
		a2 = new ArgumentHelp();
		
		assertTrue(a1.compareTo(a2) == 0);
		assertTrue(a2.compareTo(a1) == 0);

		a1.setName("a");
		a2.setName("a");
		assertTrue(a1.compareTo(a2) == 0);
		assertTrue(a2.compareTo(a1) == 0);
		
		a1.setName("a");
		a2.setName("b");
		assertTrue(a1.compareTo(a2) < 0);
		assertTrue(a2.compareTo(a1) > 0);		
		
		a1.setName("a");
		a2.setName(null);
		assertTrue(a1.compareTo(a2) > 0);
		assertTrue(a2.compareTo(a1) < 0);
	}
}

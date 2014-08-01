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
		a.setType(ArgumentParameterType.STRING);
		assertEquals(ArgumentParameterType.STRING, a.getType());
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

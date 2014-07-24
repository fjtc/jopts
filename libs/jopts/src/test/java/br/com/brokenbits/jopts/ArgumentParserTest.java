package br.com.brokenbits.jopts;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArgumentParserTest {

	@Test
	public void testArgumentParser() throws Exception {
		ArgumentParser<Sample1> parser;
		
		parser = new ArgumentParser<Sample1>(Sample1.class);
		assertNotNull(parser);
	}

}

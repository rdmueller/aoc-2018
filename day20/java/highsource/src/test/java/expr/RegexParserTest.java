package expr;

import java.io.StringReader;

import org.junit.Test;

public class RegexParserTest {
	
	private static void parse(String input) throws ParseException {
		RegexParser parser = new RegexParser(new StringReader(input));
		parser.Input();
	}

	@Test
	public void parses() throws ParseException {
		parse("^N(E)N$");
		parse("^WNE$");
	}
}

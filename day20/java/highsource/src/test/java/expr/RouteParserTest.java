package expr;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;

import org.junit.Test;

public class RouteParserTest {

	private static Expr parse(String input) throws ParseException {
		final RouteParser parser = new RouteParser(new StringReader(input));
		return parser.parseExpr();
	}

	@Test
	public void parses() throws ParseException {
		assertThat(parse("^WNE$")).isEqualTo(new Route(new Step('W'), new Step('N'), new Step('E')));
		assertThat(parse("^N(E)N$")).isEqualTo(new Route(new Step('N'), new Branch(new Step('E')), new Step('N')));
		assertThat(parse("^N(E|W)N$"))
				.isEqualTo(new Route(new Step('N'), new Branch(new Step('E'), new Step('W')), new Step('N')));
		parse("^N(E|(W|E))N$");
		assertThat(parse("^N(E|(W|E))N$")).isEqualTo(new Route(new Step('N'),
				new Branch(new Step('E'), new Branch(new Step('W'), new Step('E'))), new Step('N')));
		assertThat(parse("^N(E|)N$"))
				.isEqualTo(new Route(new Step('N'), new Branch(new Step('E'), new Stand()), new Step('N')));
	}
}

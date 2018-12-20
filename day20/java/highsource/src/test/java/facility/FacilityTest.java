package facility;

import java.io.StringReader;

import org.junit.Test;

import expr.Expr;
import expr.ParseException;
import expr.RouteParser;

import static org.assertj.core.api.Assertions.*;

public class FacilityTest {

	private static Expr parse(String input) throws ParseException {
		final RouteParser parser = new RouteParser(new StringReader(input));
		return parser.parseExpr();
	}

	@Test
	public void generatesToString() {

		final Facility facility = new Facility();
		facility.step(new XY(0, 0), new XY(-1, 0));
		facility.step(new XY(-1, 0), new XY(-1, 1));

		System.out.println(facility);
	}

	@Test
	public void calculatesFurtherstPath0() throws ParseException {
		Expr expr = parse("^WNE$");
		final Facility facility = new Facility();
		facility.trace(expr);
		System.out.println(facility);
		assertThat(facility.calculateFurtherstPath()).isEqualTo(3);
	}

	@Test
	public void calculatesFurtherstPath1() throws ParseException {
		Expr expr = parse("^ENWWW(NEEE|SSE(EE|N))$");
		final Facility facility = new Facility();
		facility.trace(expr);
		System.out.println(facility);
		assertThat(facility.calculateFurtherstPath()).isEqualTo(10);
	}
	
	@Test
	public void calculatesFurtherstPath2() throws ParseException {
		Expr expr = parse("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$");
		final Facility facility = new Facility();
		facility.trace(expr);
		System.out.println(facility);
		assertThat(facility.calculateFurtherstPath()).isEqualTo(18);
	}
	
	@Test
	public void calculatesFurtherstPath3() throws ParseException {
		Expr expr = parse("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$");
		final Facility facility = new Facility();
		facility.trace(expr);
		System.out.println(facility);
		assertThat(facility.calculateFurtherstPath()).isEqualTo(23);
	}

	@Test
	public void calculatesFurtherstPath4() throws ParseException {
		Expr expr = parse("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$");
		final Facility facility = new Facility();
		facility.trace(expr);
		System.out.println(facility);
		assertThat(facility.calculateFurtherstPath()).isEqualTo(31);
	}
}

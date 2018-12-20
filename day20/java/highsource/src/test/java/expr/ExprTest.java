package expr;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.Set;

import org.junit.Test;

import facility.Facility;
import facility.XY;

public class ExprTest {

	private static Expr parse(String input) throws ParseException {
		final RouteParser parser = new RouteParser(new StringReader(input));
		return parser.parseExpr();
	}

	@Test
	public void tracesStand() {
		Facility facility = new Facility();
		facility.step(new XY(0, 0), new XY(1, 0));
		Stand stand = new Stand();
		Set<XY> endpoints = stand.traceFrom(new XY(1, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(1, 0));

	}

	@Test
	public void tracesStepE() {
		Facility facility = new Facility();
		Step step = new Step('E');
		Set<XY> endpoints = step.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(1, 0));

	}

	@Test
	public void tracesStepS() {
		Facility facility = new Facility();
		Step step = new Step('S');
		Set<XY> endpoints = step.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(0, 1));

	}

	@Test
	public void tracesBranch() {
		Facility facility = new Facility();
		Branch branch = new Branch(new Step('E'), new Step('S'));
		Set<XY> endpoints = branch.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(0, 1), new XY(1, 0));

	}
	
	@Test
	public void tracesRoute0() throws ParseException {
		Facility facility = new Facility();
		Expr route = parse("^ENWWW$");
		Set<XY> endpoints = route.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(-2, -1));

	}

	@Test
	public void tracesRoute1() throws ParseException {
		Facility facility = new Facility();
		Expr route = parse("^ENWWW(NEEE|SSE(EE|N))$");
		Set<XY> endpoints = route.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(1, -2), new XY(1, 1), new XY(-1, 0));

	}
	
	@Test
	public void tracesRoute2() throws ParseException {
		Facility facility = new Facility();
		Expr route = parse("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$");
		Set<XY> endpoints = route.traceFrom(new XY(0, 0), facility);
		System.out.println(facility);
		assertThat(endpoints).containsOnly(new XY(2, -2));

	}
	
	

}

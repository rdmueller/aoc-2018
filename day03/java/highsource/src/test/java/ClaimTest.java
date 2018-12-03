import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class ClaimTest {

	@Test
	public void parsesClaim() {
		assertThat(Claim.parse("#1409 @ 285,437: 10x26")).isEqualTo(new Claim(1409, 285, 437, 10, 26));
	}

	@Test
	public void toStrings() throws IOException {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				final Claim claim = Claim.parse(line);
				assertThat(claim.toString()).isEqualTo(line);
			}
		}
	}

	@Test
	public void area1() {
		final Claim claim = Claim.parse("#3 @ 5,5: 1x1");

		assertThat(claim.area()).containsExactlyInAnyOrder(new SquareInch(5, 5));
	}

	@Test
	public void area2x2() {
		final Claim claim = Claim.parse("#3 @ 5,5: 2x2");

		assertThat(claim.area()).containsExactlyInAnyOrder(
				//
				new SquareInch(5, 5), new SquareInch(6, 5),
				//
				new SquareInch(5, 6), new SquareInch(6, 6));
	}
}
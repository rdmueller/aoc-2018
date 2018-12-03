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
				new InputStreamReader(SolutionPart1.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				final Claim claim = Claim.parse(line);
				assertThat(claim.toString()).isEqualTo(line);
			}
		}
	}
}
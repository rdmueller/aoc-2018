import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TracksMapTest {

	@Test
	public void parsesInput() throws IOException {

		final List<String> input = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test1.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				input.add(line);
			}
		}

		TracksMap tracksMap = TracksMap.parse(input);

		assertThat(tracksMap).isNotNull();
//		assertThat(tracksMap.getCartStates()).hasSize(2);
		System.out.println(tracksMap);
	}
}

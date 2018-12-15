import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Lines {

	private Lines() {

	}

	public static List<String> resource(String resource) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getClassLoader().getResourceAsStream(resource)))) {

			final List<String> lines = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				lines.add(line);
			}
			return lines;
		}

	}

}

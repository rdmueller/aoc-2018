import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SolutionPart1 {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(SolutionPart1.class.getResourceAsStream("test1.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				System.out.println(line);
			}
		}
	}
}

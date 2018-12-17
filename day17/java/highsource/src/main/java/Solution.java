import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test0.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				XY.parse(line).forEach(System.out::println);
			}
		}
	}
}
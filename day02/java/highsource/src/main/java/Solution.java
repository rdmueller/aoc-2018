import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			int numberOfLinesContainingExactlyTwoOfAnyLetter = 0;
			int numberOfLinesContainingExactlyThreeOfAnyLetter = 0;

			for (String line; (line = reader.readLine()) != null;) {
				final Map<Integer, Long> letterCounts = countLetters(line);
				final Collection<Long> counts = letterCounts.values();

				numberOfLinesContainingExactlyTwoOfAnyLetter += (counts.contains(2L) ? 1 : 0);
				numberOfLinesContainingExactlyThreeOfAnyLetter += (counts.contains(3L) ? 1 : 0);
			}

			long checksum = numberOfLinesContainingExactlyTwoOfAnyLetter
					* numberOfLinesContainingExactlyThreeOfAnyLetter;
			System.out.println("Checksum: " + checksum);
		}
	}

	private static Map<Integer, Long> countLetters(String str) {
		return str.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
}

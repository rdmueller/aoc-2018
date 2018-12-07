import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

	private static final String REGEX = "^Step (.) must be finished before step (.) can begin.$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static void main(String[] args) throws IOException {

		final Map<Character, Collection<Character>> graph = new TreeMap<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test1.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				Matcher matcher = PATTERN.matcher(line);
				if (!matcher.matches()) {
					throw new IllegalArgumentException();
				} else {
					Character from = matcher.group(1).charAt(0);
					Character to = matcher.group(2).charAt(0);
					System.out.println(from + "->" + to);
					graph.computeIfAbsent(from, c -> new TreeSet<>()).add(to);
				}
			}
		}
		System.out.println(graph);
	}
}
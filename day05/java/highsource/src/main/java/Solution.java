import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				System.out.println("Result part 1: " + solvePart1(line).length());
				System.out.println("Result part 2: " + solvePart2(line).length());
			}
		}
	}

	private static String solvePart1(String str) {
		return fullyReact(new StringBuilder(str)).toString();
	}

	private static StringBuilder fullyReact(final StringBuilder stringBuilder) {
		do {
		} while (react(stringBuilder));
		return stringBuilder;
	}

	private static boolean react(final StringBuilder stringBuilder) {
		boolean somethingWasRemoved;
		somethingWasRemoved = false;
		for (int index = 0; index < stringBuilder.length() - 1; index++) {
			char currentChar = stringBuilder.charAt(index);
			char nextChar = stringBuilder.charAt(index + 1);
			if (Character.isUpperCase(currentChar) && (Character.toLowerCase(currentChar) == nextChar)
					|| Character.isLowerCase(currentChar) && (Character.toUpperCase(currentChar) == nextChar)) {
				stringBuilder.delete(index, index + 2);
				somethingWasRemoved = true;
			}
		}
		return somethingWasRemoved;
	}

	private static String solvePart2(String str) {
		return

		str.chars().mapToObj(c -> (char) Character.toLowerCase(c)).distinct().
		//
				map(c -> removeUnitType(new StringBuilder(str), c)).
				//
				map(Solution::fullyReact).
				//
				min(Comparator.comparingInt(StringBuilder::length)).
				//
				map(Object::toString).
				//
				orElseThrow(IllegalArgumentException::new);
	}

	private static StringBuilder removeUnitType(StringBuilder stringBuilder, char c) {
		for (int index = 0; index < stringBuilder.length() - 1; index++) {
			char currentChar = stringBuilder.charAt(index);
			if (Character.toLowerCase(currentChar) == Character.toLowerCase(c)) {
				stringBuilder.delete(index, index + 1);
				index--;
			}
		}
		return stringBuilder;
	}
}
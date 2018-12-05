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
		return fullyReact(new StringBuffer(str)).toString();
	}

	private static StringBuffer fullyReact(final StringBuffer stringBuffer) {
		do {
		} while (react(stringBuffer));
		return stringBuffer;
	}

	private static boolean react(final StringBuffer stringBuffer) {
		boolean somethingWasRemoved;
		somethingWasRemoved = false;
		for (int index = 0; index < stringBuffer.length() - 1; index++) {
			char currentChar = stringBuffer.charAt(index);
			char nextChar = stringBuffer.charAt(index + 1);
			if (Character.isUpperCase(currentChar) && (Character.toLowerCase(currentChar) == nextChar)
					|| Character.isLowerCase(currentChar) && (Character.toUpperCase(currentChar) == nextChar)) {
				stringBuffer.delete(index, index + 2);
				somethingWasRemoved = true;
			}
		}
		return somethingWasRemoved;
	}

	private static String solvePart2(String str) {
		return

		str.chars().mapToObj(c -> (char) Character.toLowerCase(c)).distinct().
		//
				map(c -> removeUnitType(new StringBuffer(str), c)).
				//
				map(Solution::fullyReact).
				//
				min(Comparator.comparingInt(StringBuffer::length)).
				//
				map(Object::toString).
				//
				orElseThrow(IllegalArgumentException::new);
	}

	private static StringBuffer removeUnitType(StringBuffer stringBuffer, char c) {
		for (int index = 0; index < stringBuffer.length() - 1; index++) {
			char currentChar = stringBuffer.charAt(index);
			if (Character.toLowerCase(currentChar) == Character.toLowerCase(c)) {
				stringBuffer.delete(index, index + 1);
				index--;
			}
		}
		return stringBuffer;
	}
}
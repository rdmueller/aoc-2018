import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
	
	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {
				solvePart1(line);
			}
		}
	}

	private static String solvePart1(String str) {
		final StringBuffer stringBuffer = new StringBuffer(str);

		boolean somethingWasRemoved = false;
		do {
			somethingWasRemoved = false;
			for (int index = 0; index < stringBuffer.length() - 1; index++) {
				char currentChar = stringBuffer.charAt(index);
				char nextChar = stringBuffer.charAt(index + 1);
				if (Character.isUpperCase(currentChar) && (Character.toLowerCase(currentChar) == nextChar) ||
						Character.isLowerCase(currentChar) && (Character.toUpperCase(currentChar) == nextChar)) {
					stringBuffer.delete(index, index + 2);
					somethingWasRemoved = true;
				}
			}
		} while (somethingWasRemoved);
		System.out.println("Result:" + stringBuffer.length());
		return stringBuffer.toString();
	}
}
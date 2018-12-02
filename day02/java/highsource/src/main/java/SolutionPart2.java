import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SolutionPart2 {

	public static void main(String[] args) throws IOException {
		
		List<String> lines = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(SolutionPart2.class.getResourceAsStream("input.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				lines.add(line);
			}
		}
		
		for (String left : lines) {
			for (String right : lines) {
				
				String commonPart = commonPart(left, right);
				if (commonPart.length() == left.length() - 1) {
					System.out.println("Common part: " + commonPart);
				}
			}
		}
	}
	
	private static String commonPart(String left, String right) {
		
		
		if (left.length() != right.length()) {
			throw new IllegalArgumentException("Strings must have equal length.");
		}
		
		char[] leftChars = left.toCharArray();
		char[] rightChars = right.toCharArray();
		
		StringBuffer commonPart = new StringBuffer();
		
		for (int index = 0; index < leftChars.length; index++) {
			char leftChar = leftChars[index];
			char rightChar = rightChars[index];
			if (leftChar == rightChar) {
				commonPart.append(leftChar);
			}
		}
		return commonPart.toString(); 
	}
}

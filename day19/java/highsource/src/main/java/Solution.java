import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import op.Op;

public class Solution {

	private static final String REGEX = "#ip (\\d+)";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			int boundToRegister = -1;

			for (String line; (line = reader.readLine()) != null;) {
				Matcher matcher = PATTERN.matcher(line);
				if (matcher.matches()) {
					boundToRegister = Integer.parseInt(matcher.group(1));
				} else {
					Op op = Op.parse(line);
					System.out.println(op);
				}
			}

			if (boundToRegister == -1) {
				throw new IllegalArgumentException("Bound to register was not set.");
			}

			System.out.println("Bound to register:" + boundToRegister);
		}
	}
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

import point.XYZT;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Set<XYZT> points = new LinkedHashSet<>();
			
			for (String line; (line = reader.readLine()) != null;) {
				points.add(XYZT.parse(line));
			}
			System.out.println(points);
		}
	}
}
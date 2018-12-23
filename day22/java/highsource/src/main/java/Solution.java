import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cave.Cave;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			int depth = -1;
			int targetX = -1;
			int targetY = -1;

			for (String line; (line = reader.readLine()) != null;) {
				System.out.println(line);
				Matcher depthMatcher = Pattern.compile("^depth: (\\d+)$").matcher(line);
				if (depthMatcher.matches()) {
					depth = Integer.parseInt(depthMatcher.group(1));
				}

				Matcher targetMatcher = Pattern.compile("^target: (\\d+),(\\d+)$").matcher(line);
				if (targetMatcher.matches()) {
					targetX = Integer.parseInt(targetMatcher.group(1));
					targetY = Integer.parseInt(targetMatcher.group(2));
				}
			}

			System.out.println(depth);
			System.out.println(targetX);
			System.out.println(targetY);
			
			final Cave cave = new Cave(depth, targetX, targetY);
			// Part 1
			{
				
				int riskLevel = 0;
				for (int y = 0; y <= targetY; y++) {
					for (int x = 0; x <= targetX; x++) {
						riskLevel += cave.calculateRegionType(x, y).getRiskLevel();
					}
				}				
				System.out.println(">>>>>>>> " + riskLevel);
			}
			// Part 2
			{
				System.out.println(">>>>>>>> " + cave.walk());
			}
		}
	}
}
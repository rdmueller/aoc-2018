import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import point.Constellation;
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
			
			
			// Part 1
			{
				final Set<Constellation> constellations = new LinkedHashSet<>();
				
				points.stream().forEach(point -> {
					final Constellation constellation;
					final Set<Constellation> closeEnoughConstellations = constellations.stream().filter(c -> c.isCloseEnough(point)).collect(Collectors.toSet());
					if (closeEnoughConstellations.isEmpty()) {
						constellation = new Constellation(point);
						constellations.add(constellation);
					}
					else {
						closeEnoughConstellations.add(new Constellation(point));
						constellation = Constellation.merge(closeEnoughConstellations);
						constellations.removeAll(closeEnoughConstellations);
						constellations.add(constellation);
					}
				});
				
				System.out.println(constellations.size());
				
			}
		}
	}
}
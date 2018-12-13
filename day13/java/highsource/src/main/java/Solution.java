import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			List<String> rawMap = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				rawMap.add(line);
			}

			// Part 1
			{
				TracksMap tracksMap = TracksMap.parse(rawMap);

				Set<XY> collisions = tracksMap.getCollisions();

				while (collisions.isEmpty()) {
					tracksMap.tick();
					collisions = tracksMap.getCollisions();
				}
				collisions.forEach(xy -> System.out.println("First collision at [" + xy + "]."));
			}
		}
	}
}
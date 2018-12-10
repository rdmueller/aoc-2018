import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test1.txt")))) {

			List<PointOfLight> pointsOfLight = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {

				final PointOfLight pointOfLight = PointOfLight.parse(line);
				pointsOfLight.add(pointOfLight);
				System.out.println(pointOfLight);
			}
		}
	}
}
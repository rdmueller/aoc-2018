import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Sky sky = new Sky();

			for (String line; (line = reader.readLine()) != null;) {

				final PointOfLight pointOfLight = PointOfLight.parse(line);
				sky.add(pointOfLight);
			}

			for (int i = 0; i < 20000; i++) {
				XY leftBottom = sky.leftBottom();
				XY rightTop = sky.rightTop();
				if (Math.abs(leftBottom.getX() - rightTop.getX()) < 128
						&& Math.abs(leftBottom.getX() - rightTop.getX()) < 128) {
					System.out.println("=[" + i + "]==============================================");
					sky.draw(System.out);
				}
				sky.next();
			}
		}
	}
}
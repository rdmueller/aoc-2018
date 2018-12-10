import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Sky sky = new Sky();

			for (String line; (line = reader.readLine()) != null;) {

				final PointOfLight pointOfLight = PointOfLight.parse(line);
				sky.add(pointOfLight);
			}

			try (FileOutputStream fos = new FileOutputStream("output.txt"); PrintStream ps = new PrintStream(fos);) {
				for (int i = 0; i < 20000; i++) {
					ps.println("=[" + i + "]==============================================");
					XY leftBottom = sky.leftBottom();
					XY rightTop = sky.rightTop();
					if (Math.abs(leftBottom.getX() - rightTop.getX()) < 64) {
						sky.draw(ps);
					}
					ps.println(sky.leftBottom() + " " + sky.rightTop());
					sky.next();
				}
			}
		}
	}
}
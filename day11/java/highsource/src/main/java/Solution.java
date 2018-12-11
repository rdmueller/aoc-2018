import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {

				final int serialNumber = Integer.parseInt(line);
				System.out.println(serialNumber);

				// Part 1
				{
					XY max = Grid.findMax(serialNumber, 3);
					System.out.println(max);
				}
				// Part 2
				{
					XYSize max = Grid.findMax(serialNumber);
					System.out.println(max);
				}
			}
		}
	}
}
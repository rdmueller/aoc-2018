import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import nanobot.Nanobot;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final List<Nanobot> nanobots = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				Nanobot nanobot = Nanobot.parse(line);
				nanobots.add(nanobot);
//				System.out.println(nanobot);
			}
//			System.out.println(nanobots.size());

			// Part 1
			{
				final Nanobot nanobotWithLargestSignalRadius = nanobots.stream().max(Comparator.comparingInt(Nanobot::getR))
						.orElseThrow(IllegalStateException::new);

				final long nanobotsInRange = nanobots.stream().filter(nanobotWithLargestSignalRadius::isInRange)
						.count();
				System.out.println("Number of nanobots in range of the toughest nanobot is: " + nanobotsInRange);

			}
		}
	}
}
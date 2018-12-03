import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionPart1 {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(SolutionPart1.class.getResourceAsStream("input.txt")))) {

			final Map<SquareInch, AtomicInteger> fabric = new TreeMap<>(
					Comparator.<SquareInch>comparingInt(o -> o.x).thenComparing(o -> o.y));

			for (String line; (line = reader.readLine()) != null;) {
				Claim claim = Claim.parse(line);
				claim.area().stream()
						.forEach(xy -> fabric.computeIfAbsent(xy, ignored -> new AtomicInteger(0)).incrementAndGet());
			}

			final long numberOfSquareInchesOfFabricWithinTwoOrMoreClaims = fabric.entrySet().stream()
					.filter(entry -> entry.getValue().get() >= 2).count();
			System.out.println("Number of square inches of fabric within two or more claims:"
					+ numberOfSquareInchesOfFabricWithinTwoOrMoreClaims);
		}
	}
}

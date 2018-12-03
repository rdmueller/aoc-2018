import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Map<SquareInch, Set<Claim>> fabric = new TreeMap<>(
					Comparator.<SquareInch>comparingInt(o -> o.x).thenComparing(o -> o.y));

			final Set<Claim> claims = new HashSet<>();

			for (String line; (line = reader.readLine()) != null;) {
				Claim claim = Claim.parse(line);
				claims.add(claim);
				claim.area().stream().forEach(xy -> fabric.computeIfAbsent(xy, ignored -> new HashSet<>()).add(claim));
			}

			final long numberOfSquareInchesOfFabricWithinTwoOrMoreClaims = fabric.entrySet().stream()
					.filter(entry -> entry.getValue().size() >= 2).count();
			System.out.println("Number of square inches of fabric within two or more claims:"
					+ numberOfSquareInchesOfFabricWithinTwoOrMoreClaims);

			fabric.entrySet().stream().map(Entry::getValue).filter(cs -> cs.size() >= 2).flatMap(Collection::stream)
					.forEach(claims::remove);

			System.out.println("Non-overlapping claims:" + claims);

		}
	}
}

package point;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Constellation {

	private final Set<XYZT> points = new HashSet<>();

	private Constellation(Set<XYZT> points) {
		this.points.addAll(points);
	}

	public Constellation(XYZT point) {
		Objects.requireNonNull(point, "point must not be null.");
		points.add(point);
	}

	public boolean isCloseEnough(XYZT point) {
		Objects.requireNonNull(point, "point must not be null.");
		return points.stream().anyMatch(p -> point.inSameConstellation(p));
	}

	public static Constellation merge(Collection<Constellation> constellations) {
		final Set<XYZT> points = constellations.stream().map(c -> c.points).flatMap(Collection::stream)
				.collect(Collectors.toSet());
		return new Constellation(points);
	}

}

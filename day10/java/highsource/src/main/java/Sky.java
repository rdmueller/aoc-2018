import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Sky {

	private final List<PointOfLight> pointsOfLight = new ArrayList<>();

	public void add(PointOfLight pointOfLight) {
		Objects.requireNonNull(pointOfLight, "pointOfLight must not be null.");
		this.pointsOfLight.add(pointOfLight);
	}
	
	public void next() {
		this.pointsOfLight.forEach(PointOfLight::next);
	}
	
	public int area() {
		final int minX = pointsOfLight.stream().mapToInt(PointOfLight::getX).min()
				.orElseThrow(IllegalStateException::new);
		final int minY = pointsOfLight.stream().mapToInt(PointOfLight::getY).min()
				.orElseThrow(IllegalStateException::new);
		final int maxX = pointsOfLight.stream().mapToInt(PointOfLight::getX).max()
				.orElseThrow(IllegalStateException::new);
		final int maxY = pointsOfLight.stream().mapToInt(PointOfLight::getY).max()
				.orElseThrow(IllegalStateException::new);
		return Math.abs(maxX - minX) * Math.abs(maxY - minY);
	}
	
	public XY leftBottom() {
		final int minX = pointsOfLight.stream().mapToInt(PointOfLight::getX).min()
				.orElseThrow(IllegalStateException::new);
		final int minY = pointsOfLight.stream().mapToInt(PointOfLight::getY).min()
				.orElseThrow(IllegalStateException::new);
		return new XY(minX, minY);
	}
	
	public XY rightTop() {
		final int maxX = pointsOfLight.stream().mapToInt(PointOfLight::getX).max()
				.orElseThrow(IllegalStateException::new);
		final int maxY = pointsOfLight.stream().mapToInt(PointOfLight::getY).max()
				.orElseThrow(IllegalStateException::new);
		return new XY(maxX, maxY);
	}
	

	public void draw(PrintStream ps) {

		final int minX = pointsOfLight.stream().mapToInt(PointOfLight::getX).min()
				.orElseThrow(IllegalStateException::new);
		final int minY = pointsOfLight.stream().mapToInt(PointOfLight::getY).min()
				.orElseThrow(IllegalStateException::new);
		final int maxX = pointsOfLight.stream().mapToInt(PointOfLight::getX).max()
				.orElseThrow(IllegalStateException::new);
		final int maxY = pointsOfLight.stream().mapToInt(PointOfLight::getY).max()
				.orElseThrow(IllegalStateException::new);

		for (int y = minY; y <= maxY; y++) {
			final int currentY = y;

			final Set<Integer> xs = pointsOfLight.stream().filter(pol -> (pol.getY() == currentY))
					.map(PointOfLight::getX).collect(Collectors.toSet());

			for (int x = minX; x <= maxX; x++) {

				ps.print(xs.contains(x) ? '#' : '.');
			}
			ps.println();
		}
	}
}

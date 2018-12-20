package facility;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import expr.Expr;

public class Facility {
	private final Set<XY> rooms = new HashSet<>();
	private final Map<XY, Set<XY>> doors = new HashMap<>();

	public Set<XY> trace(Expr expr) {
		return expr.traceFrom(new XY(0, 0), this);
	}

	public Facility step(XY from, XY to) {
		Objects.requireNonNull(from, "from must not be null.");
		Objects.requireNonNull(to, "to must not be null.");
		rooms.add(from);
		rooms.add(to);
		doors.computeIfAbsent(from, ignored -> new HashSet<>()).add(to);
		doors.computeIfAbsent(to, ignored -> new HashSet<>()).add(from);
		return this;
	}

	public int calculateFurtherstPath() {

		final XY initialRoom = new XY(0,0);
		
		final Map<XY, Integer> distanceByRoom = new HashMap<>();
		distanceByRoom.put(initialRoom, 0);
		final NavigableSet<XY> roomsToVisit = new TreeSet<>(Comparator.comparingInt(XY::getY).thenComparingInt(XY::getX));
		
		roomsToVisit.add(initialRoom);

		while (!roomsToVisit.isEmpty()) {
			final XY currentRoom = roomsToVisit.pollFirst();
			final int currentDistance = distanceByRoom.get(currentRoom);
			final Set<XY> neighbours = doors.getOrDefault(currentRoom, Collections.emptySet());
			for (XY neighbour : neighbours) {
				final int neighbourDistance = distanceByRoom.getOrDefault(neighbour, Integer.MAX_VALUE);
				if (currentDistance + 1 < neighbourDistance) {
					distanceByRoom.put(neighbour, currentDistance + 1);
					roomsToVisit.add(neighbour);
				}
			}
		}

		return distanceByRoom.values().stream().mapToInt(Integer::intValue).max()
				.orElseThrow(IllegalStateException::new);

	}

	@Override
	public String toString() {

		int minX = rooms.stream().mapToInt(XY::getX).min().orElseThrow(IllegalStateException::new);
		int minY = rooms.stream().mapToInt(XY::getY).min().orElseThrow(IllegalStateException::new);
		int maxX = rooms.stream().mapToInt(XY::getX).max().orElseThrow(IllegalStateException::new);
		int maxY = rooms.stream().mapToInt(XY::getY).max().orElseThrow(IllegalStateException::new);

		final StringBuilder sb = new StringBuilder();

		for (int x = minX; x <= maxX; x++) {
			if (x == minX) {
				sb.append('#');
			}
			sb.append('#');
			sb.append('#');
		}
		sb.append('\n');
		for (int y = minY; y <= maxY; y++) {

			for (int x = minX; x <= maxX; x++) {
				final XY current = new XY(x, y);
				final XY e = new XY(x + 1, y);
				final Set<XY> currentDoors = doors.getOrDefault(current, Collections.emptySet());
				if (x == minX) {
					sb.append('#');
				}
				if (x == 0 && y == 0) {
					sb.append('X');
				} else if (rooms.contains(current)) {
					sb.append('.');
				} else {
					sb.append('#');
				}
				if (currentDoors.contains(e)) {
					sb.append('|');
				} else {
					sb.append('#');
				}
			}
			sb.append('\n');

			for (int x = minX; x <= maxX; x++) {
				final XY current = new XY(x, y);
				final XY s = new XY(x, y + 1);
				final Set<XY> currentDoors = doors.getOrDefault(current, Collections.emptySet());
				if (x == minX) {
					sb.append('#');
				}
				if (currentDoors.contains(s)) {
					sb.append('-');
				} else {
					sb.append('#');
				}
				sb.append('#');
			}
			sb.append('\n');
		}
		return sb.toString();
	}

}

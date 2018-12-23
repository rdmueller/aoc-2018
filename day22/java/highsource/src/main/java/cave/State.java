package cave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class State {

	private final int x;
	private final int y;
	private final Tool tool;

	public Collection<State> walk(Cave cave) {

		List<State> walkableNeighbours = new ArrayList<>(4);

		// Left
		if (x > 0) {
			if (tool.suitableFor(cave.calculateRegionType(x - 1, y))) {
				walkableNeighbours.add(new State(x - 1, y, tool));
			}
		}
		// Up
		if (y > 0) {
			if (tool.suitableFor(cave.calculateRegionType(x, y - 1))) {
				walkableNeighbours.add(new State(x, y - 1, tool));
			}
		}
		// Right
		if (x < cave.getWidth() - 1) {
			if (tool.suitableFor(cave.calculateRegionType(x + 1, y))) {
				walkableNeighbours.add(new State(x + 1, y, tool));
			}
		}
		// Down
		if (y < cave.getHeight() - 1) {
			if (tool.suitableFor(cave.calculateRegionType(x, y + 1))) {
				walkableNeighbours.add(new State(x, y + 1, tool));
			}
		}
		return walkableNeighbours;
	}

	public Collection<State> change(Cave cave) {

		final RegionType regionType = cave.calculateRegionType(this.x, this.y);

		return Arrays.asList(Tool.values()).stream().
		//
				filter(t -> t != this.tool).
				//
				filter(t -> t.suitableFor(regionType)).
				//
				map(t -> new State(this.x, this.y, t)).
				//
				collect(Collectors.toList());
	}
}

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Board {

	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
	private final char[][] data;
	private final int width;
	private final int height;

	public Board(int minX, int minY, int maxX, int maxY, char[][] data) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.data = data;
		this.width = maxX - minX + 1;
		this.height = maxY - minY + 1;
	}
	
	public int countWaterTiles() {
		int count = 0;
	
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				if (data[y][x] == '#' || data[y][x] == '.' || data[y][x] == '+') {
					// Do not count
				}
				else {
					count++;
				}
			}}
		return count;
	}
	
	public int countWaterAtRestTiles() {
		int count = 0;
	
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				if (data[y][x] == '~') {
					count++;
				}
			}}
		return count;
	}
	
	public boolean flow() {
		boolean changed = false;
		for (int y = 1; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {

				char current = data[y][x];
				char above = data[y - 1][x];
				char left = data[y][x - 1];
				char right = data[y][x + 1];
				char below = data[y + 1][x];

				if (false) {

				}
				// Flow down from well
				else if (current == '.' && above == '+') {
					data[y][x] = '|';
					changed = true;
				}
				// Flow down
				else if (current == '.' && above == '|') {
					data[y][x] = '|';
					changed = true;
				}
				// Reached clay
				else if (current == '|' && stopsWater(below)) {
					data[y][x] = '-';
					changed = true;
				}
				// Distribute to right
				else if (current == '.' && stopsWater(below) && isStoppingWater(left)) {
					data[y][x] = '-';
					changed = true;
				}
				// Distribute to right waterfall
				else if (current == '.' && below == '.' &&  isStoppingWater(left)) {
					data[y][x] = '|';
					changed = true;
				}
				// Distribute to left
				else if (current == '.' && stopsWater(below) && isStoppingWater(right)) {
					data[y][x] = '-';
					changed = true;
				}
				// Distribute to left waterfall
				else if (current == '.' && below == '.' && isStoppingWater(right)) {
					data[y][x] = '|';
					changed = true;
				}
				// Reached wall on right
				else if (current == '-' && stopsWater(below) && right == '#') {
					data[y][x] = ']';
					changed = true;
				} else if (current == '-' && stopsWater(below) && right == ']') {
					data[y][x] = ']';
					changed = true;
				}
				// Reached wall on left
				else if (current == '-' && stopsWater(below) && left == '#') {
					data[y][x] = '[';
					changed = true;
				} else if (current == '-' && stopsWater(below) && left == '[') {
					data[y][x] = '[';
					changed = true;
				}
				// Reached wall on left and now on right
				else if (current == '[' && right == '#') {
					data[y][x] = '~';
					changed = true;
				} else if (current == '[' && right == ']') {
					data[y][x] = '~';
					changed = true;
				} else if (current == '[' && right == '~') {
					data[y][x] = '~';
					changed = true;
				// Reached wall on right and now on left
				} else if (current == ']' && left == '#') {
					data[y][x] = '~';
					changed = true;
				} else if (current == ']' && left == '[') {
					data[y][x] = '~';
					changed = true;
				} else if (current == ']' && left == '~') {
					data[y][x] = '~';
					changed = true;

//				} else if (current == '.' && below != '.' && right == ']') {
//					data[y][x] = ']';
//					changed = true;
//				} else if (current == '|' && below == '#' && right == '#') {
//					data[y][x] = ']';
//					changed = true;
//				} else if (current == '|' && below == '#' && left == '#') {
//					data[y][x] = '[';
//					changed = true;
				}
			}
		}
		return changed;
	}
	
	private boolean isStoppingWater(char c) {
		return c == '-' || c == ']' || c == '[';
	}
	
	private boolean stopsWater(char c) {
		return c == '#' || c == '~';
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			if (y > 0) {
				sb.append('\n');
			}
			sb.append(data[y]);
		}
		return sb.toString();
	}

	public static class Builder {
		private List<XY> clay = new ArrayList<>(32768);
		private int minX = Integer.MAX_VALUE;
		private int minY = Integer.MAX_VALUE;
		private int maxX = Integer.MIN_VALUE;
		private int maxY = Integer.MIN_VALUE;

		public Builder addClay(int x, int y) {
			return addClay(new XY(x, y));
		}

		public Builder addClay(XY xy) {
			clay.add(xy);
			minX = Math.min(minX, xy.getX());
			minY = Math.min(minY, xy.getY());
			maxX = Math.max(maxX, xy.getX());
			maxY = Math.max(maxY, xy.getY());
			return this;
		}

		public Board build() {
			if (minX > maxX || minY > maxY) {
				throw new IllegalStateException();
			}

			if (minX > 500 || maxX < 500) {
				throw new IllegalArgumentException();
			}

			int width = maxX - minX + 5;
			int height = maxY - minY + 3;
			final char[][] data = new char[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					data[y][x] = '.';
				}
			}

			data[0][499 - minX + 2] = '+';

			clay.forEach(xy -> data[xy.getY() - minY + 1][xy.getX() - minX + 2] = '#');

			return new Board(minX - 2, minY - 1, maxX + 2, maxY + 1, data);
		}
	}
}

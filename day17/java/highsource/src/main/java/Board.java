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

			int width = maxX - minX + 3;
			int height = maxY - minY + 3;
			final char[][] data = new char[height][width];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					data[y][x] = '.';
				}
			}
			
			data[0][500 - minX + 1] = '+';

			clay.forEach(xy -> data[xy.getY() - minY + 1][xy.getX() - minX + 1] = '#');

			return new Board(minX - 1, minY - 1, maxX + 1, maxY + 1, data);
		}
	}
}

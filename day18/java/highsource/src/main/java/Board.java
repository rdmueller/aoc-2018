import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Board {

	private final int width;
	private final int height;
	private final char[][] data;

	public int resourceValue() {

		int numberOfTrees = 0;
		int numberOfLumberyard = 0;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = data[y][x];
				if (c == '|') {
					numberOfTrees++;
				} else if (c == '#') {
					numberOfLumberyard++;
				}
			}
		}
		
		return numberOfTrees * numberOfLumberyard;
	}

	public Board next() {

		char[][] next = new char[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				char c = data[y][x];

				char[] adjacent = new char[] {
						//
						get(x - 1, y - 1), get(x, y - 1), get(x + 1, y - 1),
						//
						get(x - 1, y), get(x + 1, y),
						//
						get(x - 1, y + 1), get(x, y + 1), get(x + 1, y + 1) };

				int numberOfOpen = 0;
				int numberOfTrees = 0;
				int numberOfLumberyard = 0;
				for (int i = 0; i < adjacent.length; i++) {
					char a = adjacent[i];
					if (a == '.') {
						numberOfOpen++;
					} else if (a == '|') {
						numberOfTrees++;
					} else if (a == '#') {
						numberOfLumberyard++;
					}
				}

				final char n;

				if (c == '.') {
					if (numberOfTrees >= 3) {
						n = '|';
					} else {
						n = c;
					}
				} else if (c == '|') {
					if (numberOfLumberyard >= 3) {
						n = '#';
					} else {
						n = c;
					}
				} else if (c == '#') {
					if (numberOfTrees >= 1 && numberOfLumberyard >= 1) {
						n = '#';
					} else {
						n = '.';
					}
				} else {
					throw new IllegalStateException();
				}

				next[y][x] = n;
			}
		}

		return new Board(width, height, next);
	}

	private char get(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return '?';
		} else {
			return data[y][x];
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			if (y > 0) {
				sb.append('\n');
			}
			sb.append(data[y]);
		}
		return sb.toString();
	}

	public static class Builder {

		private List<String> lines = new ArrayList<String>();

		public Builder add(String line) {
			Objects.requireNonNull(line, "line must not be null.");
			lines.add(line);
			return this;
		}

		public Board build() {
			if (lines.isEmpty()) {
				throw new IllegalStateException();
			}

			final int height = lines.size();
			final int width = lines.stream().mapToInt(String::length).max().orElseThrow(IllegalStateException::new);

			final char[][] data = new char[height][width];

			for (int y = 0; y < height; y++) {
				final String line = lines.get(y);
				if (line.length() != width) {
					throw new IllegalStateException();
				}
				data[y] = line.toCharArray();
			}
			return new Board(width, height, data);
		}
	}
}

import java.util.ArrayList;
import java.util.List;

public class Board {

	private final char[][] squares;
	private final Unit[][] unitSquares;
	private final int width;
	private final int height;
	private final List<Unit> units = new ArrayList<>();
	private final List<Elf> elves = new ArrayList<>();
	private final List<Goblin> goblins = new ArrayList<>();

	private Board(char[][] squares) {
		this.squares = squares;
		this.height = squares.length;
		this.width = squares[0].length;
		this.unitSquares = new Unit[height][width];
//		for (int y =0; y < height;y++) {
//			System.out.println(this.unitSquares[y].length);
//			this.unitSquares[y] = new Unit[width];
//		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (squares[y][x] == 'E') {
					Elf elf = new Elf(new XY(x, y));
					unitSquares[y][x] = elf;
					units.add(elf);
					elves.add(elf);
				}
				if (squares[y][x] == 'G') {
					Goblin goblin = new Goblin(new XY(x, y));
					unitSquares[y][x] = goblin;
					units.add(goblin);
					goblins.add(goblin);
				}
			}
		}
	}
	
	public List<Elf> getElves() {
		return elves;
	}
	
	public List<Goblin> getGoblins() {
		return goblins;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int y = 0; y < height; y++) {
			if (y > 0) {
				sb.append('\n');
			}
			for (int x = 0; x < width; x++) {
				char c = squares[y][x];
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static Board parse(List<String> data) {

		int height = data.size();
		int width = data.stream().mapToInt(String::length).max().orElseThrow(IllegalArgumentException::new);

		char[][] squares = new char[height][width];

		for (int y = 0; y < height; y++) {
			String line = data.get(y);
			char[] row = line.toCharArray();
			if (row.length != width) {
				throw new IllegalArgumentException();
			} else {
				squares[y] = row;
			}
		}
		return new Board(squares);
	}
}

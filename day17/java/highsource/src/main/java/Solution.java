import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			Board.Builder builder = new Board.Builder();
			for (String line; (line = reader.readLine()) != null;) {
				XY.parse(line).forEach(builder::addClay);
			}

			Board board = builder.build();
//			System.out.println(board);
			int step = 0;
			while (board.flow()) {
				System.out.println(step++);
				// System.out.println(board);
			}
			// System.out.println(board);
			System.out.println(board.countWaterTiles());
			System.out.println(board.countWaterAtRestTiles());
		}
	}
}
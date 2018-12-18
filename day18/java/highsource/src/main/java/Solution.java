import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Board.Builder builder = new Board.Builder();

			for (String line; (line = reader.readLine()) != null;) {
				builder.add(line);
			}

			Board initialBoard = builder.build();
			System.out.println(initialBoard);

			// Part 1
			{
				Board board = initialBoard;

				System.out.println(board);

				for (int step = 0; step < 10; step++) {
					board = board.next();

					System.out.println("=[" + step + "]=========================");
					System.out.println(board.toString());
				}

				System.out.println("Resource value after 10 steps:" + board.resourceValue());
			}
		}
	}
}
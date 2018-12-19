import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final Board.Builder builder = new Board.Builder();

			for (String line; (line = reader.readLine()) != null;) {
				builder.add(line);
			}

			Board initialBoard = builder.build();
//			System.out.println(initialBoard);

			// Part 1
			{
				Board board = initialBoard;

				for (int step = 0; step < 10; step++) {
					board = board.next();

//					System.out.println("=[" + step + "]=========================");
//					System.out.println(board.toString());
				}

				System.out.println("Resource value after 10 steps: " + board.resourceValue());
			}

			// Part 2
			{
				Board board = initialBoard;

				Map<Board, Integer> boardOnStep = new HashMap<>();

				for (int step = 0; step < 1000000000; step++) {
					board = board.next();
					Integer oldStep = boardOnStep.put(board, step);
					if (oldStep != null) {
						final int period = step - oldStep;

//						System.out.println("Step [" + step + "]. The same board was seen on step [" + oldStep+ "]. Period is [" + period + "].");

						final int start = oldStep % period;

						if ((999999999 % period) == start) {
							System.out.println("Resource value after 1000000000 steps: " + board.resourceValue());
							break;
						}
					}
				}
			}
		}
	}
}
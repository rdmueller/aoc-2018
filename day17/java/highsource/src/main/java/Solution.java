import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test0.txt")))) {

			AtomicInteger count = new AtomicInteger(0);
			Board.Builder builder = new Board.Builder();
			for (String line; (line = reader.readLine()) != null;) {
				XY.parse(line).forEach(builder::addClay);
			}
			
			Board board = builder.build();
			System.out.println(board);
		}
	}
}
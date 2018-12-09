import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test1.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				Game game = GameSetup.parse(line).game();
				Player winner = game.play();
				
				System.out.println(
						game.getPlayers().size() + 
						" players; last marble is worth " + 
						(game.getNumberOfMarbles() - 1) + 
						" points: high score is " +
						winner.getScore());
			}
		}
	}
}
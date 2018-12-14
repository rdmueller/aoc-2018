import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

	public static void main(String[] args) throws IOException {

		// Part 1
		{
			final Recipes recipes = new Recipes();
			final int input = 890691;
			while (recipes.getScores().size() < input) {
				recipes.addNewRecipes();
				recipes.moveElves();
			}
			while (recipes.getScores().size() < input + 10) {
				recipes.addNewRecipes();
				recipes.moveElves();
			}

			String result = recipes.getScores().subList(input, input + 10).stream().mapToInt(Byte::intValue)
					.map(x -> x + '0').mapToObj(x -> Character.toString((char) x)).collect(Collectors.joining(""));

			System.out.println(result);
		}

		// Part 2
		{
			final Recipes recipes = new Recipes();
			final String input = "890691";
			int l = input.length() + 2;
			while (true) {
				recipes.addNewRecipes();
				recipes.moveElves();

				List<Byte> scores = recipes.getScores();

				if (scores.size() >= l) {
					StringBuilder sb = new StringBuilder();

					for (int scoreIndex = scores.size() - l; scoreIndex < scores.size(); scoreIndex++) {
						sb.append((char) (scores.get(scoreIndex) + '0'));
					}

					final int indexOfStr = sb.indexOf(input);
					if (indexOfStr >= 0) {
						int result = scores.size() - l + indexOfStr;
						System.out.println(result);
						break;
					}
				}
			}

		}
	}
}
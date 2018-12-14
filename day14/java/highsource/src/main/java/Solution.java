import java.io.IOException;
import java.util.stream.Collectors;

public class Solution {

	public static void main(String[] args) throws IOException {

		final Recipes recipes = new Recipes();
//		System.out.println(recipes);
		int after = 890691;
		while (recipes.getScores().size() < after) {
			recipes.addNewRecipes();
			recipes.moveElves();
//			System.out.println(recipes);
		}
//		System.out.println("============================");
		while (recipes.getScores().size() < after + 10) {
			recipes.addNewRecipes();
			recipes.moveElves();
//			System.out.println(recipes);
		}

		String result = recipes.getScores().subList(after, after + 10).stream().mapToInt(Byte::intValue)
				.map(x -> x + '0').mapToObj(x -> Character.toString((char) x)).collect(Collectors.joining(""));

		System.out.println(result);

	}
}
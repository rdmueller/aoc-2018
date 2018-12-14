import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Recipes {

	private final List<Byte> scores = new ArrayList<>(1024 * 1204);

	private final Elf elf0;
	private final Elf elf1;

	public Recipes() {
		this(0, 1, 3, 7);
	}

	public Recipes(int elf0CurrentRecipeIndex, int elf1CurrentRecipeIndex, int... scores) {
		this.elf0 = new Elf(elf0CurrentRecipeIndex);
		this.elf1 = new Elf(elf1CurrentRecipeIndex);
		for (int scoreIndex = 0; scoreIndex < scores.length; scoreIndex++) {
			this.scores.add((byte) scores[scoreIndex]);
		}
	}

	public Recipes addNewRecipes() {
		byte sumOfCurrentRecipesScores = (byte) (scores.get(elf0.getCurrentRecipeIndex())
				+ scores.get(elf1.getCurrentRecipeIndex()));

		if (sumOfCurrentRecipesScores < 10) {
			scores.add(sumOfCurrentRecipesScores);
		} else {
			scores.add((byte) (sumOfCurrentRecipesScores / 10));
			scores.add((byte) (sumOfCurrentRecipesScores % 10));
		}
		return this;
	}

	public Recipes moveElves() {
		Stream.of(this.elf0, this.elf1).forEach(elf -> {
			int currentRecipeIndex = elf.getCurrentRecipeIndex();
			byte scoreOfCurrentRecipe = this.scores.get(currentRecipeIndex);
			int stepsToMove = scoreOfCurrentRecipe + 1;
			int newCurrentRecipeIndex = (currentRecipeIndex + stepsToMove) % this.scores.size();
			elf.setCurrentRecipeIndex(newCurrentRecipeIndex);
		});
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int scoreIndex = 0; scoreIndex < scores.size(); scoreIndex++) {
			byte score = scores.get(scoreIndex);
			char scoreChar = (char) ('0' + (char) score);

			if (scoreIndex == elf0.getCurrentRecipeIndex()) {
				sb.append('(');
			} else if (scoreIndex == elf1.getCurrentRecipeIndex()) {
				sb.append('[');
			} else {
				sb.append(' ');
			}
			sb.append(scoreChar);
			if (scoreIndex == elf0.getCurrentRecipeIndex()) {
				sb.append(')');
			} else if (scoreIndex == elf1.getCurrentRecipeIndex()) {
				sb.append(']');
			} else {
				sb.append(' ');
			}

		}
		return sb.toString();
	}
}
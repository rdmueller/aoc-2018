import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Recipes {

	private final List<Byte> scores = new ArrayList<>(1024 * 1204);

	private final Elf elf0;
	private final Elf elf1;

	public Recipes() {
		this(0, 1, (byte) 3, (byte) 7);
	}

	public Recipes(int elf0CurrentRecipeIndex, int elf1CurrentRecipeIndex, byte... scores) {
		this.elf0 = new Elf(elf0CurrentRecipeIndex);
		this.elf1 = new Elf(elf1CurrentRecipeIndex);
		for (int scoreIndex = 0; scoreIndex < scores.length; scoreIndex++) {
			this.scores.add(scores[scoreIndex]);
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int scoreIndex = 0; scoreIndex < scores.size(); scoreIndex++) {
			byte score = scores.get(scoreIndex);
			char scoreChar = (char)('0' + (char) score);
			
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

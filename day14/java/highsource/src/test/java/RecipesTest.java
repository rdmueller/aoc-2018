
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RecipesTest {

	@Test
	public void generatesToString() {

		assertThat(new Recipes().toString()).isEqualTo("(3)[7]");

		assertThat(new Recipes(4, 3, 3, 7, 1, 0, 1, 0).toString()).isEqualTo(" 3  7  1 [0](1) 0 ");
	}

	@Test
	public void addsNewRecipes() {

		assertThat(new Recipes(0, 1, 3, 7).addNewRecipes()).isEqualTo(new Recipes(0, 1, 3, 7, 1, 0));

		assertThat(new Recipes(15, 10, 3, 7, 1, 0, 1, 0, 1, 2, 4, 5, 1, 5, 8, 9, 1, 6, 7).addNewRecipes())
				.isEqualTo(new Recipes(15, 10, 3, 7, 1, 0, 1, 0, 1, 2, 4, 5, 1, 5, 8, 9, 1, 6, 7, 7));
	}
	
	@Test
	public void movesElves() {

		assertThat(new Recipes(0, 1, 3, 7, 1, 0).moveElves()).isEqualTo(new Recipes(0, 1, 3, 7, 1, 0));

		assertThat(new Recipes(15, 10, 3, 7, 1, 0, 1, 0, 1, 2, 4, 5, 1, 5, 8, 9, 1, 6, 7, 7).moveElves())
				.isEqualTo(new Recipes(4, 12, 3, 7, 1, 0, 1, 0, 1, 2, 4, 5, 1, 5, 8, 9, 1, 6, 7, 7));
	}}

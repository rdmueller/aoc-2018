
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RecipesTest {

	@Test
	public void generatesToString() {

		assertThat(new Recipes().toString()).isEqualTo("(3)[7]");

		assertThat(new Recipes(4, 3, (byte) 3, (byte) 7, (byte) 1, (byte) 0, (byte) 1, (byte) 0).toString())
				.isEqualTo(" 3  7  1 [0](1) 0 ");
	}
}

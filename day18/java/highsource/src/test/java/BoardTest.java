import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BoardTest {

	@Test
	public void builds() {
		final Board.Builder builder = new Board.Builder();

		assertThat(builder.add(".#").add("#.").build().toString()).isEqualTo(
				//
				".#\n" +
				//
						"#.");
	}

}

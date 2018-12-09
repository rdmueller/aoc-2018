import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GameSetupTest {

	@Test
	public void parsesInput() {
		assertThat(GameSetup.parse("10 players; last marble is worth 1618 points")).isEqualTo(new GameSetup(10, 1618));
	}

}

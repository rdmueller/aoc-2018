import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GridTest {

	@Test
	public void calculatesLevelAt() {

		assertThat(Grid.levelAt(8, 3, 5)).isEqualTo(4);
		assertThat(Grid.levelAt(57, 122, 79)).isEqualTo(-5);
		assertThat(Grid.levelAt(39, 217, 196)).isEqualTo(0);
		assertThat(Grid.levelAt(71, 101, 153)).isEqualTo(4);
	}
	
	@Test
	public void calculatesTotalPowerLevelAt() {
		assertThat(Grid.totalPowerAt(18, 33, 45, 3)).isEqualTo(29);
		assertThat(Grid.totalPowerAt(42, 21, 61, 3)).isEqualTo(30);
	}
	
	@Test
	public void findsMax3x3() {
		assertThat(Grid.findMax(18, 3)).isEqualTo(new XY(33, 45));
		assertThat(Grid.findMax(42, 3)).isEqualTo(new XY(21, 61));
		assertThat(Grid.findMax(9306, 3)).isEqualTo(new XY(235, 38));
	}
}

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GridTest {

	@Test
	public void calculatesLevelAt() {

		assertThat(Grid.levelAt(8, 3, 5)).isEqualTo(4);
		assertThat(Grid.levelAt(57, 122, 79)).isEqualTo(-5);
		assertThat(Grid.levelAt(39, 217, 196)).isEqualTo(0);
		assertThat(Grid.levelAt(71, 101, 153)).isEqualTo(4);
//		
//		
//		Fuel cell at  122,79, grid serial number 57: power level -5.
//		Fuel cell at 217,196, grid serial number 39: power level  0.
//		Fuel cell at 101,153, grid serial number 71: power level  4.		
	}
}

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class XYTest {

	@Test
	public void parses() {

		assertThat(XY.parse("y=3, x=5..7")).containsExactly(new XY(5, 3), new XY(6, 3), new XY(7, 3));
		assertThat(XY.parse("x=5, y=2..4")).containsExactly(new XY(5, 2), new XY(5, 3), new XY(5, 4));
		

	}

}

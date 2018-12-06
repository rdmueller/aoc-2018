import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CoordTest {

	@Test
	public void parses() {
		assertThat(Coord.parse("285, 152")).isEqualTo(new Coord(285, 152));
	}
}

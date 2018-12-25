package point;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class XYZTTest {

	@Test
	public void parses() {

		assertThat(XYZT.parse("-6,1,-4,-7")).isEqualTo(new XYZT(-6, 1, -4, -7));
		assertThat(XYZT.parse("8,1,-3,0")).isEqualTo(new XYZT(8, 1, -3, 0));
	}
}

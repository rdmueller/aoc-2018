import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PointOfLightTest {

	@Test
	public void parses() {
		assertThat(PointOfLight.parse("position=<10, -3> velocity=<-1,  1>"))
				.isEqualTo(new PointOfLight(10, -3, -1, 1));
	}
}

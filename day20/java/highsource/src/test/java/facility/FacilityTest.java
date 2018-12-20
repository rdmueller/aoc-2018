package facility;

import org.junit.Test;

public class FacilityTest {

	@Test
	public void generatesToString() {

		final Facility facility = new Facility();
		facility.step(new XY(0, 0), new XY(-1, 0));
		facility.step(new XY(-1, 0), new XY(-1, 1));

		System.out.println(facility);
	}
}

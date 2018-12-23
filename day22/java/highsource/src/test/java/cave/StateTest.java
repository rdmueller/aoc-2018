package cave;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StateTest {
	@Test
	public void walks() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(new State(0, 0, Tool.NEITHER).walk(cave)).containsOnly(new State(1, 0, Tool.NEITHER));
		assertThat(new State(1010, 1510, Tool.NEITHER).walk(cave)).containsOnly(new State(1009, 1510, Tool.NEITHER), new State(1010, 1509, Tool.NEITHER));

		assertThat(new State(0, 0, Tool.CLIMBING_GEAR).walk(cave)).containsOnly(new State(1, 0, Tool.CLIMBING_GEAR),new State(0, 1, Tool.CLIMBING_GEAR));
		assertThat(new State(1010, 1510, Tool.CLIMBING_GEAR).walk(cave)).containsOnly(new State(1010, 1509, Tool.CLIMBING_GEAR));

		assertThat(new State(0, 0, Tool.TORCH).walk(cave)).containsOnly(new State(0, 1, Tool.TORCH));
		assertThat(new State(1010, 1510, Tool.TORCH).walk(cave)).containsOnly(new State(1009, 1510, Tool.TORCH));
	}
	
	@Test
	public void changes() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateRegionType(0, 1)).isEqualTo(RegionType.ROCKY);
		assertThat(new State(0, 1, Tool.NEITHER).change(cave)).containsOnly(
				new State(0, 1, Tool.CLIMBING_GEAR), new State(0, 1, Tool.TORCH));
		assertThat(new State(0, 1, Tool.TORCH).change(cave)).containsOnly(
				new State(0, 1, Tool.CLIMBING_GEAR));
		assertThat(new State(0, 1, Tool.CLIMBING_GEAR).change(cave)).containsOnly(
				new State(0, 1, Tool.TORCH));
		
		assertThat(cave.calculateRegionType(1, 1)).isEqualTo(RegionType.NARROW);
		assertThat(new State(1, 1, Tool.NEITHER).change(cave)).containsOnly(
				new State(1, 1, Tool.TORCH));
		assertThat(new State(1, 1, Tool.TORCH).change(cave)).containsOnly(
				new State(1, 1, Tool.NEITHER));
		
		assertThat(cave.calculateRegionType(2, 1)).isEqualTo(RegionType.WET);
		assertThat(new State(2, 1, Tool.NEITHER).change(cave)).containsOnly(
				new State(2, 1, Tool.CLIMBING_GEAR));
		assertThat(new State(2, 1, Tool.CLIMBING_GEAR).change(cave)).containsOnly(
				new State(2, 1, Tool.NEITHER));
	}
	
}

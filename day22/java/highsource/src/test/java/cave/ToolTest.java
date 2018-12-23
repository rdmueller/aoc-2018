package cave;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class ToolTest {

	@Test
	public void suitableFor() {
		assertThat(Arrays.asList(Tool.values()).stream().filter(t -> t.suitableFor(RegionType.ROCKY)))
				.containsOnly(Tool.CLIMBING_GEAR, Tool.TORCH);
		assertThat(Arrays.asList(Tool.values()).stream().filter(t -> t.suitableFor(RegionType.WET)))
				.containsOnly(Tool.CLIMBING_GEAR, Tool.NEITHER);
		assertThat(Arrays.asList(Tool.values()).stream().filter(t -> t.suitableFor(RegionType.NARROW)))
				.containsOnly(Tool.TORCH, Tool.NEITHER);

	}

}

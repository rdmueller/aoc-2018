package cave;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CaveTest {

	@Test
	public void calculatesGeologicIndex() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateGeologicIndex(0, 0)).isEqualTo(0);
		assertThat(cave.calculateGeologicIndex(1, 0)).isEqualTo(16807);
		assertThat(cave.calculateGeologicIndex(0, 1)).isEqualTo(48271);
		assertThat(cave.calculateGeologicIndex(1, 1)).isEqualTo(145722555);
		assertThat(cave.calculateGeologicIndex(10, 10)).isEqualTo(0);
		assertThat(cave.calculateGeologicIndex(500, 500)).isEqualTo(70005981);
	}

	@Test
	public void calculatesErosionLevel() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateErosionLevel(0, 0)).isEqualTo(510);
		assertThat(cave.calculateErosionLevel(1, 0)).isEqualTo(17317);
		assertThat(cave.calculateErosionLevel(0, 1)).isEqualTo(8415);
		assertThat(cave.calculateErosionLevel(1, 1)).isEqualTo(1805);
		assertThat(cave.calculateErosionLevel(10, 10)).isEqualTo(510);
		assertThat(cave.calculateErosionLevel(500, 500)).isEqualTo(11847);
	}

	@Test
	public void calculatesRegionType() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateRegionType(0, 0)).isEqualTo(RegionType.ROCKY);
		assertThat(cave.calculateRegionType(1, 0)).isEqualTo(RegionType.WET);
		assertThat(cave.calculateRegionType(0, 1)).isEqualTo(RegionType.ROCKY);
		assertThat(cave.calculateRegionType(1, 1)).isEqualTo(RegionType.NARROW);
		assertThat(cave.calculateRegionType(10, 10)).isEqualTo(RegionType.ROCKY);
		assertThat(cave.calculateRegionType(500, 500)).isEqualTo(RegionType.ROCKY);
	}

	@Test
	public void calculatesRiskLevel() {

		final Cave cave = new Cave(510, 10, 10);
		
		int riskLevel = 0;
		for (int y = 0; y <= 10; y++) {
			for (int x = 0; x <= 10; x++) {
				riskLevel += cave.calculateRegionType(x, y).getRiskLevel();
			}
		}
		assertThat(riskLevel).isEqualTo(114);
	}
	
	@Test
	public void walks() {

		final Cave cave = new Cave(510, 10, 10);
		
		Integer walk = cave.walk();
		
		System.out.println(walk);
	}
}
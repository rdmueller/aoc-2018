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
	public void walks510_10_10() {

		final Cave cave = new Cave(510, 10, 10);
		
//		System.out.println(cave.walkTo(new State(0, 0, Tool.TORCH))); // 0
//		System.out.println(cave.walkTo(new State(0, 1, Tool.TORCH))); // 1
//		System.out.println(cave.walkTo(new State(1, 1, Tool.TORCH)));  // 2
//		System.out.println(cave.walkTo(new State(1, 1, Tool.NEITHER)));  // 9
//		System.out.println(cave.walkTo(new State(2, 1, Tool.NEITHER))); // 10
//		System.out.println(cave.walkTo(new State(3, 1, Tool.NEITHER))); // 11
//		System.out.println(cave.walkTo(new State(4, 1, Tool.NEITHER))); // 12
//		System.out.println(cave.walkTo(new State(4, 1, Tool.CLIMBING_GEAR))); // 19
//		System.out.println(cave.walkTo(new State(4, 2, Tool.CLIMBING_GEAR))); // 20
//		System.out.println(cave.walkTo(new State(4, 3, Tool.CLIMBING_GEAR))); // 21
//		System.out.println(cave.walkTo(new State(4, 4, Tool.CLIMBING_GEAR))); // 22
//		System.out.println(cave.walkTo(new State(4, 5, Tool.CLIMBING_GEAR))); // 23
//		System.out.println(cave.walkTo(new State(4, 6, Tool.CLIMBING_GEAR))); // 24
//		System.out.println(cave.walkTo(new State(4, 7, Tool.CLIMBING_GEAR))); // 25
		System.out.print("4, 8 ==> ");
		System.out.println(cave.walkTo(new State(4, 8, Tool.CLIMBING_GEAR))); // 26
		System.out.print("5, 8 ==> ");
		System.out.println(cave.walkTo(new State(5, 8, Tool.CLIMBING_GEAR))); // 27
//		System.out.println(cave.walkTo(new State(5, 9, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(5, 10, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(5, 11, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(6, 11, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(6, 12, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(7, 12, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(8, 12, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(9, 12, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(10, 12, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(10, 11, Tool.CLIMBING_GEAR)));
//		System.out.println(cave.walkTo(new State(10, 10, Tool.CLIMBING_GEAR)));
//		
		System.out.println(cave.walkTo(new State(10, 10, Tool.TORCH)));
		
	}
}
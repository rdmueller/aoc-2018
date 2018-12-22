package cave;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CaveTest {

	@Test
	public void calculatesGeologicalIndex() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateGeologicIndex(0, 0)).isEqualTo(0);
		assertThat(cave.calculateGeologicIndex(1, 0)).isEqualTo(16807);
		assertThat(cave.calculateGeologicIndex(0, 1)).isEqualTo(48271);
		assertThat(cave.calculateGeologicIndex(1, 1)).isEqualTo(145722555);
//		assertThat(cave.calculateGeologicIndex(100000, 100000)).isEqualTo(145722555);
	}

	@Test
	public void calculatesErosionLevel() {

		final Cave cave = new Cave(510, 10, 10);

		assertThat(cave.calculateErosionLevel(0, 0)).isEqualTo(510);
		assertThat(cave.calculateErosionLevel(1, 0)).isEqualTo(17317);
		assertThat(cave.calculateErosionLevel(0, 1)).isEqualTo(8415);
		assertThat(cave.calculateErosionLevel(1, 1)).isEqualTo(1805);
//		assertThat(cave.calculateGeologicIndex(100000, 100000)).isEqualTo(145722555);
	}

}

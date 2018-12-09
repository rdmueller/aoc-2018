import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;

public class MarbleCircleTest {

	@Test
	public void toStrings() {

		MarbleCircle circle = new MarbleCircle();

		{
			circle.place(new Marble(0));
			assertThat(circle.toString()).isEqualTo("(0)");
		}
		{
			circle.place(new Marble(1));
			assertThat(circle.toString()).isEqualTo("0 (1)");
		}

	}
	
	@Test
	public void marble0Placed() {

		MarbleCircle circle = new MarbleCircle();
		{
			circle.place(new Marble(0));
			assertThat(circle.toString()).isEqualTo("(0)");
		}
	}
	
	@Test
	public void marble1Placed() {

		MarbleCircle circle = new MarbleCircle();
		{
			circle.place(new Marble(0));
			assertThat(circle.toString()).isEqualTo("(0)");
		}
		{
			circle.place(new Marble(1));
			assertThat(circle.toString()).isEqualTo("0 (1)");
		}
	}

	@Test
	public void marble2Placed() {

		MarbleCircle circle = new MarbleCircle();
		{
			circle.place(new Marble(0));
			assertThat(circle.toString()).isEqualTo("(0)");
		}
		{
			circle.place(new Marble(1));
			assertThat(circle.toString()).isEqualTo("0 (1)");
		}
		{
			circle.place(new Marble(2));
			assertThat(circle.toString()).isEqualTo("0 (2) 1");
		}
	}
	
	@Test
	public void marble3Placed() {

		MarbleCircle circle = new MarbleCircle();
		{
			circle.place(new Marble(0));
			assertThat(circle.toString()).isEqualTo("(0)");
		}
		{
			circle.place(new Marble(1));
			assertThat(circle.toString()).isEqualTo("0 (1)");
		}
		{
			circle.place(new Marble(2));
			assertThat(circle.toString()).isEqualTo("0 (2) 1");
		}
		{
			circle.place(new Marble(3));
			assertThat(circle.toString()).isEqualTo("0 2 1 (3)");
		}
	}

	@Test
	public void marble22Placed() {

		MarbleCircle circle = new MarbleCircle();
		IntStream.range(0, 23).mapToObj(Marble::new).forEach(circle::place);
		assertThat(circle.toString()).isEqualTo("0 16 8 17 4 18 9 19 2 20 10 21 5 (22) 11 1 12 6 13 3 14 7 15");
	}
	
	@Test
	public void marble23Placed() {

		MarbleCircle circle = new MarbleCircle();
		IntStream.range(0, 24).mapToObj(Marble::new).forEach(circle::place);
		assertThat(circle.toString()).isEqualTo("0 16 8 17 4 18 (19) 2 20 10 21 5 22 11 1 12 6 13 3 14 7 15");
	}
	
}

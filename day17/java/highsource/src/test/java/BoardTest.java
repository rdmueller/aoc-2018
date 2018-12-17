import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class BoardTest {

	@Test
	public void builds() {

		final Board.Builder builder = new Board.Builder();

		final Board board = builder
				//
				.addClay(499, 20)
				//
				.addClay(499, 21)
				//
				.addClay(499, 22)
				//
				.addClay(500, 22)
				//
				.addClay(501, 22)
				//
				.addClay(501, 21)
				//
				.addClay(501, 20)
				//
				.build();

		assertThat(board).isEqualTo(new Board(498, 19, 502, 23, new char[][] {
				//
				{ '.', '.', '+' , '.', '.'},
				//
				{  '.','#', '.', '#' , '.'},
				//
				{  '.','#', '.', '#' , '.'},
				//
				{  '.','#', '#', '#', '.' },
				{ '.', '.', '.' , '.', '.'}}));

		assertThat(board.toString()).isEqualTo(
				//
				"..+..\n" +
				//
				".#.#.\n" +
				//
				".#.#.\n" +
				//
				".###.\n" + 
				//
				"....." );
	}
}

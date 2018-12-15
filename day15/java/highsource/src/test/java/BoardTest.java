import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BoardTest {

	@Test
	public void parses() throws IOException {

		List<String> lines = Lines.resource("test10.txt");
		Board board = Board.parse(lines);
		System.out.println(board);
		assertThat(board.toString()).isEqualTo(lines.stream().collect(Collectors.joining("\n")));

		assertThat(board.getElves()).containsExactly(new Elf(new XY(2, 1)));
		assertThat(board.getGoblins()).containsExactly(new Goblin(new XY(4, 3)));

	}

}

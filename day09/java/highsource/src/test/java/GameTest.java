
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class GameTest {

	@Test
	public void playsWith9PlayersAnd25Marbles() {
		assertThat(new Game(9, 25).play().getScore()).isEqualTo(32);
	}

	@Test
	public void playsWith9PlayersAnd48Marbles() {
		assertThat(new Game(9, 48).play().getScore()).isEqualTo(63);
	}

	@Test
	public void playsWith1PlayersAnd48Marbles() {
		assertThat(new Game(1, 48).play().getScore()).isEqualTo(95);
	}

	@Test
	public void playsWith10PlayersAnd1618Marbles() {
		assertThat(new Game(10, 1618).play().getScore()).isEqualTo(8317);
	}

	@Test
	public void playsWith13PlayersAnd7999Marbles() {
		assertThat(new Game(13, 7999).play().getScore()).isEqualTo(146373);
	}

	@Test
	public void playsWith17PlayersAnd1104Marbles() {
		assertThat(new Game(17, 1104).play().getScore()).isEqualTo(2764);
	}

	@Test
	public void playsWith21PlayersAnd6111Marbles() {
		assertThat(new Game(21, 6111).play().getScore()).isEqualTo(54718);
	}

	@Test
	public void playsWith30PlayersAnd5807Marbles() {
		assertThat(new Game(30, 5807).play().getScore()).isEqualTo(37305);
	}

	@Test
	public void playsWith425PlayersAnd70848Marbles() {
		assertThat(new Game(425, 70848).play().getScore()).isEqualTo(413188);
	}

//	@Ignore
	@Test
	public void playsWith425PlayersAnd708480Marbles() {
		assertThat(new Game(425, 708480).play().getScore()).isEqualTo(34527442L);
	}

//	@Ignore
	@Test
	public void playsWith425PlayersAnd7084800Marbles() {
		assertThat(new Game(425, 7084800).play().getScore()).isEqualTo(3377272893L);
	}

}

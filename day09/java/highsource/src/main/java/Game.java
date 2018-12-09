import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;

@Getter
public class Game {

	private final int numberOfMarbles;
	private final MarbleCircle circle = new MarbleCircle();
	private final List<Player> players;
	private int currentPlayerIndex = 0;

	public Game(int numberOfPlayers, int lastMarbleValue) {
		numberOfMarbles = lastMarbleValue + 1;
		players = IntStream.range(0, numberOfPlayers).mapToObj(Player::new).collect(toList());
	}

	public Player play() {
		for (int marble = 1; marble < numberOfMarbles; marble++) {
			Player currentPlayer = players.get(currentPlayerIndex);
			currentPlayer.place(marble, circle);
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		}
		return winner();
	}

	private Player winner() {
		return this.players.stream().max(Comparator.comparing(Player::getScore)).orElseThrow(IllegalStateException::new);
	}
}

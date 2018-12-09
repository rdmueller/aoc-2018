import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class GameSetup {

	private final int numberOfPlayers;
	private final int lastMarbleValue;

	private static final String REGEX = "(\\d+) players; last marble is worth (\\d+) points";
	private static final Pattern PATTERN = Pattern.compile(REGEX);
	
	@Override
	public String toString() {
		return numberOfPlayers + " players; last marble is worth " + lastMarbleValue + " points";
	}

	public static final GameSetup parse(String input) {
		Objects.requireNonNull(input, "input must not be null.");
		Matcher matcher = PATTERN.matcher(input);

		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}

		return new GameSetup(Integer.valueOf(matcher.group(1)), Integer.valueOf(matcher.group(2)));
	}
}
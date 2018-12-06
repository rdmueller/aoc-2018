import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Coord {

	private final int x;
	private final int y;

	@Override
	public String toString() {
		return x + ", " + y;
	}
	
	
	private static final String REGEX = "^(\\d+), (\\d+)$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);
	
	public static Coord parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		Matcher matcher = PATTERN.matcher(str);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid input [" + str + "].");
		}
		else {
			return new Coord(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
		}
	}
}
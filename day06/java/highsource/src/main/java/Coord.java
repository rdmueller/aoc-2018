import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Coord {

	private char id = 0;
	private final int x;
	private final int y;

	@Override
	public String toString() {
		return id + ": " + x + ", " + y;
	}
	
	public boolean hasId() {
		return id != 0;
	}
	
	public int manhattanDistance(Coord coord) {
		Objects.requireNonNull(coord, "coord must not be null.");
		final Coord that = coord;
		return Math.abs(this.x - that.x) + Math.abs(this.y - that.y);  
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
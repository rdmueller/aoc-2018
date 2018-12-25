package point;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class XYZT {

	private final int x;
	private final int y;
	private final int z;
	private final int t;
	private static final int MAX_CONSTELLATION_DISTANCE = 3;

	private final static String REGEX = "^(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)$";
	private final static Pattern PATTERN = Pattern.compile(REGEX);

	public boolean inSameConstellation(XYZT that) {
		Objects.requireNonNull(that, "the other point must not be null.");
		return manhattanDistance(that) <= MAX_CONSTELLATION_DISTANCE;
	}

	public int manhattanDistance(XYZT that) {
		Objects.requireNonNull(that, "the other point must not be null.");
		return Math.abs(this.x - that.x) + Math.abs(this.y - that.y) + Math.abs(this.z - that.z)
				+ Math.abs(this.t - that.t);
	}

	public static XYZT parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		final Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(str);
		} else {
			return new XYZT(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
		}
	}

}

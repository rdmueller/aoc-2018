package nanobot;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Nanobot {

	private final int x;
	private final int y;
	private final int z;
	private final int r;

	public int manhattanDistance(Nanobot nanobot) {
		Objects.requireNonNull(nanobot, "nanobot must not be null.");
		
		return Math.abs(this.x - nanobot.x) +
				Math.abs(this.y - nanobot.y) +
				Math.abs(this.z - nanobot.z);
	}
	
	public boolean isInRange(Nanobot nanobot) {
		Objects.requireNonNull(nanobot, "nanobot must not be null.");
		return manhattanDistance(nanobot) <= this.r;
	}
	
	private static final String REGEX = "^pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)?$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static Nanobot parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		final Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(str);
		} else {

			final String xStr = matcher.group(1);
			final String yStr = matcher.group(2);
			final String zStr = matcher.group(3);
			final String rStr = matcher.group(4);
			
			final int x = Integer.parseInt(xStr);
			final int y = Integer.parseInt(yStr);
			final int z = Integer.parseInt(zStr);
			final int r = Integer.parseInt(rStr);
			return new Nanobot(x, y, z, r);
		}
	}

}

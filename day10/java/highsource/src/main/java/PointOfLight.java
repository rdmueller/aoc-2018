import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PointOfLight {

	private int x;
	private int y;

	private int vx;
	private int vy;
	
	public void next() {
		x+=vx;
		y+=vy;
	}

	@Override
	public String toString() {
		return "position=<" + x + "," + y + "> velocity=<" + vx + "," + vy + ">";
	}

	private static final String REGEX = "^position=<([^,]+),([^>]+)> velocity=<([^,]+),([^>]+)>$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static PointOfLight parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		} else {
			return new PointOfLight(
					Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim()),
					Integer.parseInt(matcher.group(3).trim()), Integer.parseInt(matcher.group(4).trim()));

		}

	}

}

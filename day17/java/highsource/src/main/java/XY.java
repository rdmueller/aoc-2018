import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class XY {

	private final int x;
	private final int y;

	private static final String REGEX_X = "^x=(\\d+), y=(\\d+)..(\\d+)$";
	private static final Pattern PATTERN_X = Pattern.compile(REGEX_X);
	private static final String REGEX_Y = "^y=(\\d+), x=(\\d+)..(\\d+)$";
	private static final Pattern PATTERN_Y = Pattern.compile(REGEX_Y);

	public static final Stream<XY> parse(String line) {
		final Matcher matcherX = PATTERN_X.matcher(line);

		if (matcherX.matches()) {
			final int x = Integer.parseInt(matcherX.group(1));
			final int y1 = Integer.parseInt(matcherX.group(2));
			final int y2 = Integer.parseInt(matcherX.group(3));
			return IntStream.range(y1, y2 + 1).mapToObj(y -> new XY(x, y));
		} else {
			final Matcher matcherY = PATTERN_Y.matcher(line);

			if (matcherY.matches()) {
				final int y = Integer.parseInt(matcherY.group(1));
				final int x1 = Integer.parseInt(matcherY.group(2));
				final int x2 = Integer.parseInt(matcherY.group(3));
				return IntStream.range(x1, x2 + 1).mapToObj(x -> new XY(x, y));

			} else {
				throw new IllegalArgumentException();
			}
		}

	}

}

package op;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import register.Registers;

@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class Op {

	private final int a;
	private final int b;
	private final int c;

	public abstract Registers apply(Registers registers);

	private static final String REGEX = "^([^\\s]+) (\\d+) (\\d+) (\\d+)$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static Op parse(String line) {
		Objects.requireNonNull(line, "line must not be null.");

		final Matcher matcher = PATTERN.matcher(line);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(line);
		} else {

			final OpType opType = OpType.valueOf(matcher.group(1).toUpperCase());

			final int a = Integer.parseInt(matcher.group(2));
			final int b = Integer.parseInt(matcher.group(3));
			final int c = Integer.parseInt(matcher.group(4));

			return opType.create(a, b, c);
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb
				//
				.append(getClass().getSimpleName().toLowerCase()).append(' ')
				//
				.append(a).append(' ')
				//
				.append(b).append(' ')
				//
				.append(c);
		return sb.toString();
	}
}

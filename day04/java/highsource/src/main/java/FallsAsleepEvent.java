import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FallsAsleepEvent extends Event {

	public static final String REGEX = "^\\[([^\\]]+)] falls asleep$";
	public static final Pattern PATTERN = Pattern.compile(REGEX);

	public FallsAsleepEvent(LocalDateTime timestamp) {
		super(timestamp);
	}

	@Override
	public String toString() {
		return "[" + this.timestamp.format(Event.FORMATTER) + "] falls asleep";
	}

	public static Event parse(String str) {
		final Matcher matcher = FallsAsleepEvent.PATTERN.matcher(str);
		if (matcher.matches()) {
			LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), Event.FORMATTER);
			return new FallsAsleepEvent(timestamp);
		} else {
			throw new IllegalArgumentException();
		}
	}
}

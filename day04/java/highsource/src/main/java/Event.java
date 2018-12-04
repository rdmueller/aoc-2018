import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class Event {

	public final LocalDateTime timestamp;
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	protected Event(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.timestamp);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		final Event that = (Event) object;
		return Objects.equals(this.timestamp, that.timestamp);
	}
	
	public abstract void applyTo(SleepLog dayLog);

	private static final List<Function<String, ? extends Event>> PARSERS = Arrays.asList(BeginsShiftEvent::parse,
			FallsAsleepEvent::parse, WakesUpEvent::parse);

	public static Event parse(String str) {
		for (Function<String, ? extends Event> parser : PARSERS) {
			try {
				return parser.apply(str);
			} catch (IllegalArgumentException ignored) {
			}
		}
		throw new IllegalArgumentException();
	}
}

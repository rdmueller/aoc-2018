package event;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sleeplog.SleepLog;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public abstract class Event {

	private final LocalDateTime timestamp;
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Override
	public String toString() {
		return "[" + this.getTimestamp().format(Event.FORMATTER) + "]";
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

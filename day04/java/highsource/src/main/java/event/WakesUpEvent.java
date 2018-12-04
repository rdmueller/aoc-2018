package event;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import sleeplog.GuardState;
import sleeplog.SleepLog;

@EqualsAndHashCode(callSuper=true)
@Getter
public class WakesUpEvent extends Event {

	public static final String REGEX = "^\\[([^\\]]+)] wakes up$";
	public static final Pattern PATTERN = Pattern.compile(REGEX);

	public WakesUpEvent(LocalDateTime timestamp) {
		super(timestamp);
	}

	@Override
	public String toString() {
		return super.toString() + " wakes up";
	}
	
	@Override
	public void applyTo(SleepLog dayLog) {
		dayLog.setStateFromTime(GuardState.AWAKE, this.getTimestamp());
	}

	public static Event parse(String str) {
		final Matcher matcher = WakesUpEvent.PATTERN.matcher(str);
		if (matcher.matches()) {
			LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), Event.FORMATTER);
			return new WakesUpEvent(timestamp);
		} else {
			throw new IllegalArgumentException();
		}
	}
}

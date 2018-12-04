package event;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import sleeplog.ShiftOnDateByGuardId;
import sleeplog.SleepLog;

@EqualsAndHashCode(callSuper=true)
@Getter
public class BeginsShiftEvent extends Event {

	private static final String REGEX = "^\\[([^\\]]+)] Guard #(\\d+) begins shift$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);
	private final int guardId;
	private final ShiftOnDateByGuardId shiftOnDateByGuardId;

	public BeginsShiftEvent(LocalDateTime timestamp, int guardId) {
		super(timestamp);
		this.guardId = guardId;
		this.shiftOnDateByGuardId = new ShiftOnDateByGuardId(timestamp.toLocalDate(), guardId);
	}

	@Override
	public String toString() {
		return super.toString() + " Guard #" + guardId + " begins shift";
	}

	@Override
	public void applyTo(SleepLog dayLog) {
	}

	public static Event parse(String str) {
		final Matcher matcher = BeginsShiftEvent.PATTERN.matcher(str);
		if (matcher.matches()) {
			LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), Event.FORMATTER);
			int guardId = Integer.parseInt(matcher.group(2));
			return new BeginsShiftEvent(timestamp, guardId);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
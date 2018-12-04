import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeginsShiftEvent extends Event {

	public static final String REGEX = "^\\[([^\\]]+)] Guard #(\\d+) begins shift$";
	public static final Pattern PATTERN = Pattern.compile(REGEX);
	public final int id;

	public BeginsShiftEvent(LocalDateTime timestamp, int id) {
		super(timestamp);
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.timestamp, this.id);
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
		final BeginsShiftEvent that = (BeginsShiftEvent) object;
		return Objects.equals(this.timestamp, that.timestamp) && this.id == that.id;
	}

	@Override
	public String toString() {
		return "[" + this.timestamp.format(Event.FORMATTER) + "] Guard #" + id + " begins shift";
	}

	public static Event parse(String str) {
		final Matcher matcher = BeginsShiftEvent.PATTERN.matcher(str);
		if (matcher.matches()) {
			LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), Event.FORMATTER);
			int id = Integer.parseInt(matcher.group(2));
			return new BeginsShiftEvent(timestamp, id);
		} else {
			throw new IllegalArgumentException();
		}
	}
}

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class SleepLog {

	public final LocalDate date;
	public final int id;

	private final List<GuardState> states = new ArrayList<>(60);
	{
		for (int index = 0; index < 60; index++) {
			states.add(GuardState.UNKNOWN);
		}
	}

	public SleepLog(DateId dateId) {
		this(dateId.date, dateId.id);
	}

	public SleepLog(LocalDate date, int id) {
		this.date = date;
		this.id = id;
	}

	public void setStateFromTime(GuardState state, LocalDateTime timestamp) {
		if (!Objects.equals(this.date, timestamp.toLocalDate())) {
			throw new IllegalArgumentException("Wrong date [" + timestamp + "], expected [" + this.date + "].");
		}
		if (timestamp.getHour() != 0) {
			throw new IllegalArgumentException("Wrong hour [" + timestamp.getHour() + "], only 0 expected.");
		}
		for (int index = timestamp.getMinute(); index < 60; index++) {
			this.states.set(index, state);
		}
	}

	public int getMinutesAsleep() {
		return (int) this.states.stream().filter(state -> state == GuardState.ASLEEP).count();
	}

	public IntStream minutesAsleep() {

		List<Integer> minutesAsleep = new ArrayList<>();
		for (int minute = 0; minute < 60; minute++) {
			if (this.states.get(minute) == GuardState.ASLEEP) {
				minutesAsleep.add(minute);
			}
		}

		return minutesAsleep.stream().mapToInt(Integer::intValue);
	}
}

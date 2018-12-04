package sleeplog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SleepLog {

	private final LocalDate date;
	private final int guardId;

	private final List<GuardState> states = new ArrayList<>(60);
	{
		for (int index = 0; index < 60; index++) {
			states.add(GuardState.UNKNOWN);
		}
	}

	public SleepLog(ShiftOnDateByGuardId dateId) {
		this(dateId.getDate(), dateId.getGuardId());
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

	public Stream<Integer> minutesAsleep() {

		List<Integer> minutesAsleep = new ArrayList<>();
		for (int minute = 0; minute < 60; minute++) {
			if (this.states.get(minute) == GuardState.ASLEEP) {
				minutesAsleep.add(minute);
			}
		}

		return minutesAsleep.stream();
	}
}

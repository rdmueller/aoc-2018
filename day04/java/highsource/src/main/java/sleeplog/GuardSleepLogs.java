package sleeplog;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GuardSleepLogs {

	private final int guardId;
	private final List<SleepLog> sleepLogs;

	public GuardSleepLogs(Entry<Integer, List<SleepLog>> entry) {
		this(entry.getKey(), entry.getValue());
	}

	public CountByMinuteByGuardId findMostSleepyMinite() {
		return sleepLogs.stream().
		// Count slept minutes
				flatMap(SleepLog::minutesAsleep).
				//
				collect(groupingBy(identity(), counting())).
				//
				entrySet().
				//
				stream().
				//
				map(entry -> new CountByMinuteByGuardId(getGuardId(), entry.getKey(), entry.getValue())).
				// Find the minute slept most
				max(Comparator.comparing(CountByMinuteByGuardId::getCount)).
				//
				orElseThrow(IllegalStateException::new);
	}

	public long getSleptDuration() {
		return this.sleepLogs.stream().collect(Collectors.summingInt(SleepLog::getMinutesAsleep));
	}
}

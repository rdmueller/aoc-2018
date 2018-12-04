import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final List<Event> events = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				Event event = Event.parse(line);
				events.add(event);
			}

			Collections.sort(events, Comparator.comparing(event -> event.timestamp));

			int currentId = -1;

			Comparator<DateId> dateIdComparator = Comparator.comparing(dateId -> dateId.date);
			dateIdComparator = dateIdComparator.thenComparingInt(dateId -> dateId.id);

			final Map<DateId, SleepLog> logs = new TreeMap<>(dateIdComparator);

			SleepLog currentLog;

			for (Event event : events) {

				if (event instanceof BeginsShiftEvent) {
					BeginsShiftEvent beginsShiftEvent = (BeginsShiftEvent) event;
					currentId = beginsShiftEvent.id;
				} else {
					final DateId currentDateId = new DateId(event.timestamp.toLocalDate(), currentId);
					currentLog = logs.computeIfAbsent(currentDateId, SleepLog::new);
					event.applyTo(currentLog);
				}
			}

			// Part 1
			{
				Map<Integer, Integer> sleepMinutesByGuard = logs.values().stream().collect(
						Collectors.groupingBy(dayLog -> dayLog.id, Collectors.summingInt(SleepLog::getMinutesAsleep)));

				Entry<Integer, Integer> mostSleepyGuardEntry = sleepMinutesByGuard.entrySet().stream()
						.max(Comparator.comparingInt(Entry::getValue)).orElseThrow(IllegalStateException::new);

				int mostSleepyGuardId = mostSleepyGuardEntry.getKey();
				int mostSleepyGuardMinutes = mostSleepyGuardEntry.getValue();

				System.out.println("The guard [" + mostSleepyGuardId + "] is the most sleepy guard, slept ["
						+ mostSleepyGuardMinutes + "] minutes.");

				Map<Integer, List<SleepLog>> logsByGuard = logs.values().stream()
						.collect(Collectors.groupingBy(dayLog -> dayLog.id));

				List<SleepLog> mostSleepyGuardLogs = logsByGuard.get(mostSleepyGuardId);

				final Map<Integer, Long> mostSleepyGuardSleptCountByMinute = mostSleepyGuardLogs.stream()
						.flatMapToInt(SleepLog::minutesAsleep).boxed()
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

				final Integer mostSleepyMinuteOfMostSleepyGuard = mostSleepyGuardSleptCountByMinute.entrySet().stream()
						.max(Comparator.comparingLong(Entry::getValue)).orElseThrow(IllegalStateException::new)
						.getKey();

				System.out.println(
						"Most sleepy guard was most sleepy on the minute [" + mostSleepyMinuteOfMostSleepyGuard + "].");

				System.out.println("The result is " + (mostSleepyGuardId * mostSleepyMinuteOfMostSleepyGuard) + "].");

			}
		}
	}
}

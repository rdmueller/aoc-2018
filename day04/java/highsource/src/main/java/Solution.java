import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final List<Event> events = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				Event event = Event.parse(line);
				events.add(event);
			}

			Collections.sort(events, comparing(event -> event.timestamp));

			int currentId = -1;

			Comparator<DateId> dateIdComparator = comparing(dateId -> dateId.date);
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
				Map<Integer, Integer> sleepMinutesByGuard = logs.values().stream()
						.collect(groupingBy(dayLog -> dayLog.id, summingInt(SleepLog::getMinutesAsleep)));

				Entry<Integer, Integer> mostSleepyGuardEntry = sleepMinutesByGuard.entrySet().stream()
						.max(comparingInt(Entry::getValue)).orElseThrow(IllegalStateException::new);

				int mostSleepyGuardId = mostSleepyGuardEntry.getKey();
				int mostSleepyGuardMinutes = mostSleepyGuardEntry.getValue();

				System.out.println("The guard [" + mostSleepyGuardId + "] is the most sleepy guard, slept ["
						+ mostSleepyGuardMinutes + "] minutes.");

				Map<Integer, List<SleepLog>> logsByGuard = logs.values().stream()
						.collect(groupingBy(dayLog -> dayLog.id));

				List<SleepLog> mostSleepyGuardLogs = logsByGuard.get(mostSleepyGuardId);

				final Map<Integer, Long> mostSleepyGuardSleptCountByMinute = mostSleepyGuardLogs.stream()
						.flatMap(SleepLog::minutesAsleep).collect(groupingBy(identity(), counting()));

				final Integer mostSleepyMinuteOfMostSleepyGuard = mostSleepyGuardSleptCountByMinute.entrySet().stream()
						.max(comparingLong(Entry::getValue)).orElseThrow(IllegalStateException::new).getKey();

				System.out.println(
						"Most sleepy guard was most sleepy on the minute [" + mostSleepyMinuteOfMostSleepyGuard + "].");

				System.out.println("The result is [" + (mostSleepyGuardId * mostSleepyMinuteOfMostSleepyGuard) + "].");

			}

			// Part 2
			{
				final Map<Integer, List<SleepLog>> sleepLogsByGuard = logs.values().stream()
						.collect(groupingBy(SleepLog::getId));

				final Function<List<SleepLog>, Entry<Integer, Long>> getMaxSleepyCountByMinute = ls -> {
					return ls.stream().
					//
					flatMap(SleepLog::minutesAsleep).
					//
					collect(groupingBy(identity(), counting())).
					//
					entrySet().
					//
					stream().
					//
					max(Entry.comparingByValue()).
					//
					orElseThrow(IllegalStateException::new);
				};

				final Map<Integer, Entry<Integer, Long>> maxSleepyCountByMinuteByGuard = sleepLogsByGuard.entrySet()
						.stream().
						//
						collect(
								//
								toMap(
										//
										Map.Entry::getKey,
										//
										entry -> getMaxSleepyCountByMinute.apply(entry.getValue())));

				final Entry<Integer, Entry<Integer, Long>> mostSleepyGuardEntry = maxSleepyCountByMinuteByGuard
						.entrySet().stream().
						//
						max(comparingLong(entry -> entry.getValue().getValue())).
						//
						orElseThrow(IllegalStateException::new);

				final Integer mostSleepyGuardId = mostSleepyGuardEntry.getKey();
				final Integer mostSleepyGuardMinute = mostSleepyGuardEntry.getValue().getKey();
				final Long mostSleepyGuardCount = mostSleepyGuardEntry.getValue().getValue();

				System.out.println("The guard [" + mostSleepyGuardId + "] is the most sleepy guard with ["
						+ mostSleepyGuardCount + "] minutes asleep on minute [" + mostSleepyGuardMinute + "].");

				System.out.println("The result is [" + (mostSleepyGuardId * mostSleepyGuardMinute) + "].");
			}
		}
	}
}
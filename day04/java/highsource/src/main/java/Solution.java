import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import event.BeginsShiftEvent;
import event.Event;
import sleeplog.CountByMinuteByGuardId;
import sleeplog.GuardSleepLogs;
import sleeplog.ShiftOnDateByGuardId;
import sleeplog.SleepLog;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final List<Event> events = new ArrayList<>();

			for (String line; (line = reader.readLine()) != null;) {
				Event event = Event.parse(line);
				events.add(event);
			}

			Collections.sort(events, comparing(Event::getTimestamp));

			int currentId = -1;

			final Map<ShiftOnDateByGuardId, SleepLog> logs = new TreeMap<>(
					//
					comparing(ShiftOnDateByGuardId::getDate).
					//
							thenComparingInt(ShiftOnDateByGuardId::getGuardId));

			SleepLog currentLog;

			for (Event event : events) {
				if (event instanceof BeginsShiftEvent) {
					BeginsShiftEvent beginsShiftEvent = (BeginsShiftEvent) event;
					currentId = beginsShiftEvent.getGuardId();
				} else {
					final ShiftOnDateByGuardId currentDateId = new ShiftOnDateByGuardId(event.getTimestamp().toLocalDate(),
							currentId);
					currentLog = logs.computeIfAbsent(currentDateId, SleepLog::new);
					event.applyTo(currentLog);
				}
			}

			// Part 1
			{

				final CountByMinuteByGuardId mostSleepyMinuteByGuard = 

						logs.values().stream()
								// Group by guard
								.collect(groupingBy(SleepLog::getGuardId)).entrySet().stream()
								// Create sleep logs per guard
								.map(GuardSleepLogs::new)
								// Find the guard with greatest slept duration
								.max(comparingLong(GuardSleepLogs::getSleptDuration))
								.orElseThrow(IllegalStateException::new)
								// Find the most sleepy minute of this guard
								.findMostSleepyMinite();

				final Integer mostSleepyGuardId = mostSleepyMinuteByGuard.getGuardId();
				final Integer mostSleepyMinuteOfMostSleepyGuard = mostSleepyMinuteByGuard.getMinute();

				System.out.println("The guard [" + mostSleepyGuardId + "] is the most sleepy guard on minute ["
						+ mostSleepyMinuteOfMostSleepyGuard + "].");

				System.out.println("The result is [" + (mostSleepyGuardId * mostSleepyMinuteOfMostSleepyGuard) + "].");
			}

			// Part 2
			{
				final CountByMinuteByGuardId mostSleepyMinuteByGuard = logs.values().stream()
						// Group by guard
						.collect(groupingBy(SleepLog::getGuardId)).entrySet().stream()
						// Create sleep logs by guard
						.map(GuardSleepLogs::new)
						// Find most sleepy minute per guard
						.map(GuardSleepLogs::findMostSleepyMinite)
						// Find most sleepy minute across guards
						.max(comparingLong(CountByMinuteByGuardId::getCount))
						//
						.orElseThrow(IllegalStateException::new);

				final Integer mostSleepyGuardId = mostSleepyMinuteByGuard.getGuardId();
				final Integer mostSleepyGuardMinute = mostSleepyMinuteByGuard.getMinute();
				final Long mostSleepyGuardCount = mostSleepyMinuteByGuard.getCount();

				System.out.println("The guard [" + mostSleepyGuardId + "] is the most sleepy guard with ["
						+ mostSleepyGuardCount + "] minutes asleep on minute [" + mostSleepyGuardMinute + "].");

				System.out.println("The result is [" + (mostSleepyGuardId * mostSleepyGuardMinute) + "].");
			}
		}
	}
}
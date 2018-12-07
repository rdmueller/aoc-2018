import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;
import static java.util.function.Function.*;

public class Solution {

	private static final String REGEX = "^Step (.) must be finished before step (.) can begin.$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);
	private static final int WORKERS_COUNT = 5;

	public static void main(String[] args) throws IOException {

		final Map<Character, Collection<Character>> outgoingEdges = new TreeMap<>();

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {
			for (String line; (line = reader.readLine()) != null;) {
				Matcher matcher = PATTERN.matcher(line);
				if (!matcher.matches()) {
					throw new IllegalArgumentException();
				} else {
					Character from = matcher.group(1).charAt(0);
					Character to = matcher.group(2).charAt(0);
					outgoingEdges.computeIfAbsent(from, c -> new TreeSet<>()).add(to);
				}
			}
		}

		final Map<Character, Collection<Character>> incomingEdges = outgoingEdges.keySet().stream()
				.collect(toMap(identity(), c -> new TreeSet<>(), (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, TreeMap::new));

		outgoingEdges.entrySet().stream().forEach(entry -> {

			final Character from = entry.getKey();
			entry.getValue().stream().forEach(to -> {
				incomingEdges.computeIfAbsent(to, c -> new TreeSet<>()).add(from);
			});
		});

		// Part 1
		{

			Set<Character> finishedSteps = new LinkedHashSet<>();
			while (finishedSteps.size() != incomingEdges.size()) {

				Character nextStep = incomingEdges.entrySet().stream()
						// Finished steps does not contain "to"
						.filter(toFrom -> !finishedSteps.contains(toFrom.getKey()))
						// Finished steps do contain all "from"
						.filter(toFrom -> finishedSteps.containsAll(toFrom.getValue()))

						.min(Entry.comparingByKey()).orElseThrow(IllegalStateException::new).getKey();
				finishedSteps.add(nextStep);
			}
			System.out.println("Order of steps: " + finishedSteps.stream().map(Object::toString).collect(joining()));
		}

		// Part 2
		{
			Set<Character> done = new LinkedHashSet<>();
			Set<Character> workInProgress = new LinkedHashSet<>();
			int time = 0;

			Worker[] workers = new Worker[WORKERS_COUNT];
			for (int workerIndex = 0; workerIndex < WORKERS_COUNT; workerIndex++) {
				workers[workerIndex] = new Worker();
			}

			do
			{
//				System.out.print(time);
				
				for (int workerIndex = 0; workerIndex < WORKERS_COUNT; workerIndex++) {
					Worker worker = workers[workerIndex];
					
					Character processedStep = worker.tick(time);
					
					if (processedStep != null) {
						done.add(processedStep);
					}
				}

				TreeSet<Character> nextSteps = incomingEdges.entrySet().stream()
						// Steps in progress does not contain "to"
						.filter(toFrom -> !workInProgress.contains(toFrom.getKey()))
						// Finished steps does not contain "to"
						.filter(toFrom -> !done.contains(toFrom.getKey()))
						// Finished steps do contain all "from"
						.filter(toFrom -> done.containsAll(toFrom.getValue())).map(Entry::getKey)
						.collect(toCollection(TreeSet::new));

				for (int workerIndex = 0; workerIndex < WORKERS_COUNT; workerIndex++) {
					Worker worker = workers[workerIndex];
					if (!nextSteps.isEmpty()) {
						if (worker.isAvailable()) {
							final Character nextStep = nextSteps.pollFirst();
							worker.startProcessingStep(nextStep, time);
							workInProgress.add(nextStep);
						}
					}
//					System.out.print("\t");
//					System.out.print(worker.getProcessingStep());
				}

//				System.out.println();
				time++;
			}
			while (done.size() != incomingEdges.size());
			System.out.println("It takes [" + (time - 1) + "] to complete all the steps.");

		}
	}
}
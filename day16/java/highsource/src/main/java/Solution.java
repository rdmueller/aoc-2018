import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import op.Op;
import op.OpType;
import register.Registers;

public class Solution {

	private static final String BEFORE_REGEX = "^Before: \\[([^\\]]+)\\]$";
	private static final Pattern BEFORE_PATTERN = Pattern.compile(BEFORE_REGEX);
	private static final String AFTER_REGEX = "^After:  \\[([^\\]]+)\\]$";
	private static final Pattern AFTER_PATTERN = Pattern.compile(AFTER_REGEX);

	public static void main(String[] args) throws IOException {

		final List<Sample> samples = new ArrayList<>();
		final List<int[]> instructions = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			for (String line; (line = reader.readLine()) != null;) {

				Matcher beforeMatcher = BEFORE_PATTERN.matcher(line);
				if (beforeMatcher.matches()) {
					String beforeStr = beforeMatcher.group(1);
					Registers before = Registers.parse(beforeStr);
					String instructionStr = reader.readLine();
					int[] instruction = Arrays.asList(instructionStr.split(" ")).stream().map(String::trim)
							.mapToInt(Integer::parseInt).toArray();
					String afterLine = reader.readLine();
					Matcher afterMatcher = AFTER_PATTERN.matcher(afterLine);
					if (!afterMatcher.matches()) {
						throw new IllegalArgumentException(afterLine);
					}
					String afterStr = afterMatcher.group(1);
					Registers after = Registers.parse(afterStr);

					final Sample sample = new Sample(before, instruction, after);
					samples.add(sample);
					reader.readLine();
				} else if (!line.isEmpty()) {
					int[] instruction = Arrays.asList(line.split(" ")).stream().map(String::trim)
							.mapToInt(Integer::parseInt).toArray();
					instructions.add(instruction);
//					System.out.println(line);
				}
			}
		}

		// Part 1
		{
			System.out.println(samples.stream().map(sample -> {
				final Registers before = sample.getBefore();
				final Registers after = sample.getAfter();
				final int[] instruction = sample.getInstruction();

				return Arrays.asList(OpType.values()).stream().map(opType -> {
					try {
						Op op = opType.create(instruction);
						Registers resultingAfter = op.apply(before);
						if (resultingAfter.equals(after)) {
							return opType;
						} else {
							return null;
						}
					} catch (IllegalArgumentException iaex) {
						return null;
					}
				}).filter(Objects::nonNull).collect(Collectors.toList());
			}).filter(ops -> ops.size() >= 3).count());
		}

		// Part 2
		{
			Map<Integer, Set<OpType>> possibleOpTypesByOpCode = new HashMap<>();

			samples.stream().forEach(sample -> {
				final Registers before = sample.getBefore();
				final Registers after = sample.getAfter();
				final int[] instruction = sample.getInstruction();
				final int opCode = instruction[0];

				Set<OpType> possibleOps = Arrays.asList(OpType.values()).stream()
						.filter(opType -> opType.create(instruction).apply(before).equals(after))
						.collect(Collectors.toSet());

				possibleOpTypesByOpCode.putIfAbsent(opCode, possibleOps);
				possibleOpTypesByOpCode.get(opCode).retainAll(possibleOps);
			});

			final Set<OpType> unique = new HashSet<>();
			while (unique.size() < OpType.values().length) {
				unique.addAll(possibleOpTypesByOpCode.values().stream().filter(v -> v.size() == 1)
						.flatMap(Collection::stream).collect(Collectors.toSet()));
				possibleOpTypesByOpCode.values().stream().filter(v -> v.size() > 1).forEach(v -> v.removeAll(unique));
			}

			final Map<Integer, OpType> opTypeByOpCode = possibleOpTypesByOpCode.entrySet().stream()
					.collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().iterator().next()));

			Registers registers = new Registers(0, 0, 0, 0);

			for (int index = 0; index < instructions.size(); index++) {
				int[] instruction = instructions.get(index);
				int opCode = instruction[0];
				OpType opType = opTypeByOpCode.get(opCode);
				Op op = opType.create(instruction);
				registers = op.apply(registers);
			}
			System.out.println(registers);
		}
	}
}
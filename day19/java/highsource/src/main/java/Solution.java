import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import op.Op;
import program.Program;
import register.Registers;

public class Solution {

	private static final String REGEX = "#ip (\\d+)";
	private static final Pattern PATTERN = Pattern.compile(REGEX);

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			int boundToRegister = -1;

			final List<Op> ops = new ArrayList<>(64);

			for (String line; (line = reader.readLine()) != null;) {
				Matcher matcher = PATTERN.matcher(line);
				if (matcher.matches()) {
					boundToRegister = Integer.parseInt(matcher.group(1));
				} else {
					Op op = Op.parse(line);
					ops.add(op);
//					System.out.println(op);
				}
			}

			if (boundToRegister == -1) {
				throw new IllegalArgumentException("Bound to register was not set.");
			}

			// Part 1
			{

				final Program program = new Program(boundToRegister, ops, new Registers(0, 0, 0, 0, 0, 0));

				while (program.execute()) { // Well, execute }
				}
				System.out.println("Registers after execution:" + program.getRegisters());
			}

			// Part 2
			{

				final Program program = new Program(boundToRegister, ops, new Registers(1, 0, 0, 0, 0, 0));
				while (program.execute()) {
					// Well, execute
					if (program.getInstructionPointer() == 3) {
						break;
					}
				}

				int magicNumber = program.getRegisters().get(5);

				int result = IntStream.range(1, magicNumber + 1).filter(n -> (magicNumber % n) == 0).sum();

				System.out.println("Register 0 will contain [" + result + "] after execution.");
			}

		}
	}
}
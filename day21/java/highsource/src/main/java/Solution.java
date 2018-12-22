import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				}
			}

			if (boundToRegister == -1) {
				throw new IllegalArgumentException("Bound to register was not set.");
			}

			// Part 1
			{

				final Program program = new Program(boundToRegister, ops, new Registers(0, 0, 0, 0, 0, 0));

				while (program.execute()) {
					if (program.getInstructionPointer() == 28) {
						System.out.print(program.getRegisters().get(3));
						break;
					}
				}
			}
			
			// Part 2
			{
				Set<BigInteger> testValues = new LinkedHashSet<>();
				
				final Program program = new Program(boundToRegister, ops, new Registers(-1, 0, 0, 0, 0, 0));

				while (program.execute()) {
					if (program.getInstructionPointer() == 28) {
						final BigInteger newValue = program.getRegisters().get(3);
						if (testValues.add(newValue)) {
							System.out.println(newValue);
						}
						else {
							break;
						}
					}
				}
			}
		}
	}
}
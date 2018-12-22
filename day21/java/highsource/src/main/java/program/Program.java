package program;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import op.Op;
import register.Registers;

@AllArgsConstructor
public class Program {

	private final int instructionPointerRegister;
	private final List<Op> ops;
	private Registers registers;

	public int getInstructionPointer() {
		return registers.get(instructionPointerRegister).intValue();
	}

	private void increaseInstructionPointer() {
		final int ip = getInstructionPointer();
		registers = registers.set(instructionPointerRegister, BigInteger.valueOf(ip + 1));
	}

	public Registers getRegisters() {
		return registers;
	}

	private static final boolean DEBUG = false;

	public boolean execute() {

		int ip = getInstructionPointer();

		if (ip < 0 || ip >= ops.size()) {
			throw new IllegalStateException();
		}

		if (DEBUG) {
			System.out.print("ip=");
			System.out.print(ip);
			System.out.print("[");
			System.out.print(registers.toString());
			System.out.print("] ");
		}

		Op op = this.ops.get(ip);

		if (DEBUG) {
			System.out.print(op);
			System.out.print(' ');
		}

		this.registers = op.apply(this.registers);

		if (DEBUG) {

			System.out.print("[");
			System.out.print(registers.toString());
			System.out.print("]");
			System.out.println();
		}

		increaseInstructionPointer();

		final int newIp = getInstructionPointer();
		if (newIp < 0 || newIp >= ops.size()) {
			return false;
		} else {
			return true;
		}
	}
}

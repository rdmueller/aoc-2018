package op;

import register.Registers;

public class SetI extends Op {

	private final int valueA;
	private final int registerB;
	private final int registerC;

	public SetI(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
		if (registerC < 0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.valueA = valueA;
		this.registerB = registerB;
		this.registerC = registerC;
	}

	@Override
	public final Registers apply(Registers registers) {
		final int a = this.valueA;
		final int c = this.registerC;
		return registers.set(c, a);
	}
}

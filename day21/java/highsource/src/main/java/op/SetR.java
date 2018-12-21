package op;

import register.Registers;

public class SetR extends Op {

	private final int registerA;
	private final int registerB;
	private final int registerC;

	public SetR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
		if (registerA < 0 || registerA >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerC < 0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.registerA = registerA;
		this.registerB = registerB;
		this.registerC = registerC;
	}

	@Override
	public Registers apply(Registers registers) {
		final int a = registers.get(this.registerA);
		final int c = this.registerC;
		return registers.set(c, a);
	}
}

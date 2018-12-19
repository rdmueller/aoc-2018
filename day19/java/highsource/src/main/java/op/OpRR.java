package op;

import register.Registers;

public abstract class OpRR extends Op{
	
	private final int registerA;
	private final int registerB;
	private final int registerC;

	public OpRR(int registerA, int registerB, int registerC) {
		if (registerA <0 || registerA >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerB <0 || registerB >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerC <0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.registerA = registerA;
		this.registerB = registerB;
		this.registerC = registerC;
	}
	
	@Override
	public final Registers apply(Registers registers) {
		int a = registers.get(this.registerA);
		int b = registers.get(this.registerB);
		int c = this.registerC;
		int result = calculate(a, b);
		return registers.set(c, result);
	}
	
	protected abstract int calculate (int a, int b);
}


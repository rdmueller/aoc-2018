package op;

import register.Registers;

public abstract class OpRI extends Op{
	
	private final int registerA;
	private final int valueB;
	private final int registerC;

	public OpRI(int registerA, int valueB, int registerC) {
		if (registerA <0 || registerA >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerC <0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.registerA = registerA;
		this.valueB = valueB;
		this.registerC = registerC;
	}
	
	@Override
	public final Registers apply(Registers registers) {
		int a = registers.get(registerA);
		int b = this.valueB;
		int c = this.registerC;
		int result = calculate(a, b);
		return registers.set(c, result);
	}
	
	protected abstract int calculate (int a, int b);
}


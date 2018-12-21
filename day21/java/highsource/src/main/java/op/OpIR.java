package op;

import register.Registers;

public abstract class OpIR extends Op{
	
	private final int valueA;
	private final int registerB;
	private final int registerC;

	public OpIR(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
		if (registerB <0 || registerB >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerC <0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.valueA = valueA;
		this.registerB = registerB;
		this.registerC = registerC;
	}
	
	@Override
	public final Registers apply(Registers registers) {
		int a = this.valueA;
		int b = registers.get(this.registerB);
		int c = this.registerC;
		int result = calculate(a, b);
		return registers.set(c, result);
	}
	
	protected abstract int calculate (int a, int b);
}


package op;

import java.math.BigInteger;

import register.Registers;

public abstract class OpIR extends Op{
	
	// TODO
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
		// TODO
		BigInteger a = BigInteger.valueOf(this.valueA);
		BigInteger b = registers.get(this.registerB);
		int c = this.registerC;
		BigInteger result = calculate(a, b);
		return registers.set(c, result);
	}
	
	protected abstract BigInteger calculate (BigInteger a, BigInteger b);
}


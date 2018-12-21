package op;

import java.math.BigInteger;

import register.Registers;

public abstract class OpRI extends Op {

	private final int registerA;
	private final int valueB;
	private final int registerC;

	public OpRI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
		if (registerA < 0 || registerA >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		if (registerC < 0 || registerC >= Registers.COUNT) {
			throw new IllegalArgumentException();
		}
		this.registerA = registerA;
		this.valueB = valueB;
		this.registerC = registerC;
	}

	@Override
	public final Registers apply(Registers registers) {
		BigInteger a = registers.get(registerA);
		BigInteger b = BigInteger.valueOf(this.valueB);
		int c = this.registerC;
		BigInteger result = calculate(a, b);
		return registers.set(c, result);
	}

	protected abstract BigInteger calculate(BigInteger a, BigInteger b);
}

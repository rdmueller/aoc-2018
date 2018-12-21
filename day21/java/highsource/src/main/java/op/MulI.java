package op;

import java.math.BigInteger;

public class MulI extends OpRI {

	public MulI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.multiply(b);
	}
}

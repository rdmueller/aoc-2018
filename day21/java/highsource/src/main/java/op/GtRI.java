package op;

import java.math.BigInteger;

public class GtRI extends OpRI {

	public GtRI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.compareTo(b) > 0 ? BigInteger.ONE : BigInteger.ZERO;
	}
}

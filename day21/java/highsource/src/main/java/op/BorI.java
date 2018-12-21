package op;

import java.math.BigInteger;

public class BorI extends OpRI {

	public BorI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.or(b);
	}
}

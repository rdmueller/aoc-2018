package op;

import java.math.BigInteger;

public class AddI extends OpRI {

	public AddI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}
	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.add(b);
	}
}

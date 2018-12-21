package op;

import java.math.BigInteger;

public class BorR extends OpRR {

	public BorR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.or(b);
	}
}
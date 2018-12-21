package op;

import java.math.BigInteger;

public class GtRR extends OpRR {

	public GtRR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.compareTo(b) > 0 ? BigInteger.ONE : BigInteger.ZERO;
	}
}

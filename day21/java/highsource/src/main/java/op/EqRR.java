package op;

import java.math.BigInteger;

public class EqRR extends OpRR {

	public EqRR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.compareTo(b) == 0 ? BigInteger.ONE : BigInteger.ZERO;
	}
}
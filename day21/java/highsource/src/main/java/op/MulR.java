package op;

import java.math.BigInteger;

public class MulR extends OpRR {

	public MulR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.multiply(b);
	}
}

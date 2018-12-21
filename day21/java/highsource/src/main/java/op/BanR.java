package op;

import java.math.BigInteger;

public class BanR extends OpRR {

	public BanR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.and(b);
	}
}

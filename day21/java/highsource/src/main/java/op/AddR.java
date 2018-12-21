package op;

import java.math.BigInteger;

public class AddR extends OpRR {

	public AddR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.add(b);
	}
}

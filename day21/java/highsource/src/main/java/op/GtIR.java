package op;

import java.math.BigInteger;

public class GtIR extends OpIR {

	public GtIR(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
	}

	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.compareTo(b) > 0 ? BigInteger.ONE : BigInteger.ZERO;
	}

}

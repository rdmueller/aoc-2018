package op;

import java.math.BigInteger;

public class EqIR extends OpIR {

	public EqIR(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
	}
	
	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.compareTo(b) == 0 ? BigInteger.ONE : BigInteger.ZERO;
	}

}

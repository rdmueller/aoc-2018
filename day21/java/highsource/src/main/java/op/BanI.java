package op;

import java.math.BigInteger;

public class BanI extends OpRI {

	public BanI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}
	
	
	@Override
	protected BigInteger calculate(BigInteger a, BigInteger b) {
		return a.and(b);
	}
}
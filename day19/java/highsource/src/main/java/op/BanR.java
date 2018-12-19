package op;

public class BanR extends OpRR {

	public BanR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a & b;
	}
}

package op;

public class GtRR extends OpRR {

	public GtRR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a > b ? 1 : 0;
	}

}

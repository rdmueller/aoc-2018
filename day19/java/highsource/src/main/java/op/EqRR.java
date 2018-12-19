package op;

public class EqRR extends OpRR {

	public EqRR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return (a == b) ? 1 : 0;
	}

}

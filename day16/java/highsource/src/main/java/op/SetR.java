package op;

public class SetR extends OpRR {

	public SetR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a;
	}
}

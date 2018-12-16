package op;

public class AddR extends OpRR {

	public AddR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a + b;
	}
}

package op;

public class MulR extends OpRR {

	public MulR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a * b;
	}
}

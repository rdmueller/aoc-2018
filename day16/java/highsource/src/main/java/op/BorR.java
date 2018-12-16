package op;

public class BorR extends OpRR{

	public BorR(int registerA, int registerB, int registerC) {
		super(registerA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a | b;
	}
}

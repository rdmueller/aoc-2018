package op;

public class BorI extends OpRI {

	public BorI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a | b;
	}
}

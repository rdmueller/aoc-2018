package op;

public class AddI extends OpRI{

	public AddI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a + b;
	}
}

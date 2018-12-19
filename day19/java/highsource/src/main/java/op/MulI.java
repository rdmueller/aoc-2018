package op;

public class MulI extends OpRI{

	public MulI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a * b;
	}
}

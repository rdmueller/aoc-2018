package op;

public class GtRI extends OpRI {

	public GtRI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a >= b ? 1 : 0;
	}

}

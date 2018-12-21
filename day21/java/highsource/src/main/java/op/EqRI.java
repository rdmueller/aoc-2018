package op;

public class EqRI extends OpRI {

	public EqRI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return (a == b) ? 1 : 0;
	}

}

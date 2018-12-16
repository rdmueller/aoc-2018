package op;

public class BanI extends OpRI{

	public BanI(int registerA, int valueB, int registerC) {
		super(registerA, valueB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a & b;
	}
}

package op;

public class GtIR extends OpIR {

	public GtIR(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a >= b ? 1 : 0;
	}

}

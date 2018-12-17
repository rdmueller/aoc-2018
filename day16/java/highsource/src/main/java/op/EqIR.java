package op;

public class EqIR extends OpIR {

	public EqIR(int valueA, int registerB, int registerC) {
		super(valueA, registerB, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return (a == b) ? 1 : 0;
	}

}

package op;

public class SetI extends OpIR {

	public SetI(int valueA, int registerB, int registerC) {
		super(valueA, 0, registerC);
	}

	@Override
	protected int calculate(int a, int b) {
		return a;
	}
}

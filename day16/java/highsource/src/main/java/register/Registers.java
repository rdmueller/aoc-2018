package register;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Registers {
	
	public static final int COUNT = 4;

	private final int[] data = new int[COUNT];

	public Registers(int r0, int r1, int r2, int r3) {
		data[0] = r0;
		data[1] = r1;
		data[2] = r2;
		data[3] = r3;
	}

	public int get(int r) {
		if (r < 0 || r >= COUNT) {
			throw new IllegalArgumentException();
		}
		return data[r];
	}

	public Registers set(int r, int v) {
		if (r < 0 || r >= COUNT) {
			throw new IllegalArgumentException();
		}
		data[r] = v;
		return this;
	}

	@Override
	public String toString() {
		return data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3];
	}

}

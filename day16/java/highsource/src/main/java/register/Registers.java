package register;

import java.util.Arrays;
import java.util.Objects;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Registers {

	public static final int COUNT = 4;

	private final int[] data;

	public Registers(int r0, int r1, int r2, int r3) {
		this.data = new int[COUNT];
		data[0] = r0;
		data[1] = r1;
		data[2] = r2;
		data[3] = r3;
	}

	private Registers(int[] data) {
		if (data.length != COUNT) {
			throw new IllegalArgumentException();
		}
		this.data = data;
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
		final int[] newData = new int[COUNT];
		System.arraycopy(this.data, 0, newData, 0, COUNT);
		newData[r] = v;
		return new Registers(newData);
	}

	@Override
	public String toString() {
		return data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3];
	}

	public static Registers parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		final int[] data = Arrays.asList(str.split(",")).stream().map(String::trim).mapToInt(Integer::parseInt)
				.toArray();
		return new Registers(data);
	}
}

package register;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Registers {

	public static final int COUNT = 6;

	private final BigInteger[] data;

	public Registers(int r0, int r1, int r2, int r3, int r4, int r5) {
		this(
				BigInteger.valueOf(r0),
				BigInteger.valueOf(r1),
				BigInteger.valueOf(r2),
				BigInteger.valueOf(r3),
				BigInteger.valueOf(r4),
				BigInteger.valueOf(r5)
				);
	}
	public Registers(BigInteger r0, BigInteger r1, BigInteger r2, BigInteger r3, BigInteger r4, BigInteger r5) {
		Objects.requireNonNull(r0, "r0 must not be null.");
		Objects.requireNonNull(r1, "r1 must not be null.");
		Objects.requireNonNull(r2, "r2 must not be null.");
		Objects.requireNonNull(r3, "r3 must not be null.");
		Objects.requireNonNull(r4, "r4 must not be null.");
		Objects.requireNonNull(r5, "r5 must not be null.");
		this.data = new BigInteger[COUNT];
		data[0] = r0;
		data[1] = r1;
		data[2] = r2;
		data[3] = r3;
		data[4] = r4;
		data[5] = r5;
	}

	private Registers(BigInteger[] data) {
		if (data.length != COUNT) {
			throw new IllegalArgumentException();
		}
		this.data = data;
	}

	public BigInteger get(int r) {
		if (r < 0 || r >= COUNT) {
			throw new IllegalArgumentException();
		}
		return data[r];
	}

	public Registers set(int r, BigInteger v) {
		if (r < 0 || r >= COUNT) {
			throw new IllegalArgumentException();
		}
		final BigInteger[] newData = new BigInteger[COUNT];
		System.arraycopy(this.data, 0, newData, 0, COUNT);
		newData[r] = v;
		return new Registers(newData);
	}

	@Override
	public String toString() {
		return data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5];
	}

	public static Registers parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		final List<BigInteger> dataList = Arrays.asList(str.split(",")).stream().map(String::trim).
				map(BigInteger::new).collect(Collectors.toList());
		final BigInteger[] data = dataList.toArray(new BigInteger[dataList.size()]);
		return new Registers(data);
	}
}

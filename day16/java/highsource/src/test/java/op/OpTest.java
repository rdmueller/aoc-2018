package op;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import register.Registers;

public class OpTest {

	@Test
	public void addr() {
		Registers registers = new Registers(2, 3, 5, 7);
		Op op = new AddR(2, 3, 1);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 3, 5, 12));
	}

	@Test
	public void mulr() {
		Registers registers = new Registers(2, 3, 1, 7);
		Op op = new MulR(0, 3, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 14, 1, 7));
	}

	@Test
	public void banr() {
		Registers registers = new Registers(0b01101, 0b01010, 3, 7);
		Op op = new BanR(0, 1, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01101, 0b01010, 3, 0b01000));
	}

	@Test
	public void borr() {
		Registers registers = new Registers(0b01001, 0b01010, 3, 7);
		Op op = new BorR(0, 1, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01001, 0b01010, 3, 0b01011));
	}

	@Test
	public void setr() {
		Registers registers = new Registers(1, 3, 0, 7);
		Op op = new SetR(3, 0, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(7, 3, 0, 7));
	}

	@Test
	public void addi() {
		Registers registers = new Registers(2, 3, 5, 7);
		Op op = new AddI(2, 3, 1);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 3, 5, 8));
	}

	@Test
	public void muli() {
		Registers registers = new Registers(2, 3, 1, 7);
		Op op = new MulI(0, 3, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 6, 1, 7));
	}

	@Test
	public void bani() {
		Registers registers = new Registers(0b01101, 13, 3, 7);
		Op op = new BanI(0, 0b01010, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01101, 13, 3, 0b01000));
	}

	@Test
	public void bori() {
		Registers registers = new Registers(0b01001, 13, 3, 7);
		Op op = new BorI(0, 0b01010, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01001, 13, 3, 0b01011));
	}

	@Test
	public void seti() {
		Registers registers = new Registers(1, 3, 0, 7);
		Op op = new SetI(13, 3, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(13, 3, 0, 7));
	}
}

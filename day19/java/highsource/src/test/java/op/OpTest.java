package op;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import register.Registers;

public class OpTest {

	@Test
	public void addr() {
		Registers registers = new Registers(2, 3, 5, 7, 10, 20);
		Op op = new AddR(2, 3, 1);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 12, 5, 7, 10, 20));
	}

	@Test
	public void mulr() {
		assertThat(new MulR(0, 3, 2).apply(new Registers(2, 3, 1, 7, 10, 20)))
				.isEqualTo(new Registers(2, 3, 14, 7, 10, 20));
		assertThat(new MulR(2, 1, 2).apply(new Registers(3, 2, 1, 1, 10, 20)))
				.isEqualTo(new Registers(3, 2, 2, 1, 10, 20));
	}

	@Test
	public void banr() {
		Registers registers = new Registers(0b01101, 0b01010, 3, 7, 10, 20);
		Op op = new BanR(0, 1, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01101, 0b01010, 0b01000, 7, 10, 20));
	}

	@Test
	public void borr() {
		Registers registers = new Registers(0b01001, 0b01010, 3, 7, 10, 20);
		Op op = new BorR(0, 1, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01001, 0b01010, 0b01011, 7, 10, 20));
	}

	@Test
	public void setr() {
		Registers registers = new Registers(1, 3, 0, 7, 10, 20);
		Op op = new SetR(3, 0, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(1, 3, 7, 7, 10, 20));
	}

	@Test
	public void addi() {
		assertThat(new AddI(2, 3, 1).apply(new Registers(2, 3, 5, 7, 10, 20)))
				.isEqualTo(new Registers(2, 8, 5, 7, 10, 20));
		assertThat(new AddI(2, 1, 2).apply(new Registers(3, 2, 1, 1, 10, 20)))
				.isEqualTo(new Registers(3, 2, 2, 1, 10, 20));
	}

	@Test
	public void muli() {
		Registers registers = new Registers(2, 3, 1, 7, 10, 20);
		Op op = new MulI(0, 3, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(2, 3, 6, 7, 10, 20));
	}

	@Test
	public void bani() {
		Registers registers = new Registers(0b01101, 13, 3, 7, 10, 20);
		Op op = new BanI(0, 0b01010, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01101, 13, 0b01000, 7, 10, 20));
	}

	@Test
	public void bori() {
		Registers registers = new Registers(0b01001, 13, 3, 7, 10, 20);
		Op op = new BorI(0, 0b01010, 2);
		assertThat(op.apply(registers)).isEqualTo(new Registers(0b01001, 13, 0b01011, 7, 10, 20));
	}

	@Test
	public void seti() {
		assertThat(new SetI(13, 3, 2).apply(new Registers(1, 3, 0, 7, 10, 20)))
				.isEqualTo(new Registers(1, 3, 13, 7, 10, 20));
		assertThat(new SetI(2, 1, 2).apply(new Registers(3, 2, 1, 1, 10, 20)))
				.isEqualTo(new Registers(3, 2, 2, 1, 10, 20));
	}

	@Test
	public void gtri() {
		Op op = new GtRI(1, 13, 0);
		assertThat(op.apply(new Registers(3, 13, 0, 125, 10, 20))).isEqualTo(new Registers(0, 13, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(3, 14, 0, 125, 10, 20))).isEqualTo(new Registers(1, 14, 0, 125, 10, 20));
	}

	@Test
	public void gtir() {
		Op op = new GtIR(14, 1, 0);
		assertThat(op.apply(new Registers(3, 13, 0, 125, 10, 20))).isEqualTo(new Registers(1, 13, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(3, 14, 0, 125, 10, 20))).isEqualTo(new Registers(0, 14, 0, 125, 10, 20));
	}

	@Test
	public void gtrr() {
		Op op = new GtRR(0, 1, 2);
		assertThat(op.apply(new Registers(14, 14, 3, 125, 10, 20))).isEqualTo(new Registers(14, 14, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(15, 14, 3, 125, 10, 20))).isEqualTo(new Registers(15, 14, 1, 125, 10, 20));
	}

	@Test
	public void eqri() {
		Op op = new EqRI(1, 14, 0);
		assertThat(op.apply(new Registers(3, 13, 0, 125, 10, 20))).isEqualTo(new Registers(0, 13, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(3, 14, 0, 125, 10, 20))).isEqualTo(new Registers(1, 14, 0, 125, 10, 20));
	}

	@Test
	public void eqir() {
		Op op = new EqIR(13, 1, 0);
		assertThat(op.apply(new Registers(3, 13, 0, 125, 10, 20))).isEqualTo(new Registers(1, 13, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(3, 14, 0, 125, 10, 20))).isEqualTo(new Registers(0, 14, 0, 125, 10, 20));
	}

	@Test
	public void Eqrr() {
		Op op = new EqRR(0, 1, 2);
		assertThat(op.apply(new Registers(13, 14, 3, 125, 10, 20))).isEqualTo(new Registers(13, 14, 0, 125, 10, 20));
		assertThat(op.apply(new Registers(14, 14, 3, 125, 10, 20))).isEqualTo(new Registers(14, 14, 1, 125, 10, 20));
	}

}

package op;

import java.util.Objects;

public enum OpType {

	ADDI {
		@Override
		public AddI create(int a, int b, int c) {
			return new AddI(a, b, c);
		}
	},
	ADDR {
		@Override
		public AddR create(int a, int b, int c) {
			return new AddR(a, b, c);
		}
	},
	BANI {
		@Override
		public BanI create(int a, int b, int c) {
			return new BanI(a, b, c);
		}
	},
	BANR {
		@Override
		public BanR create(int a, int b, int c) {
			return new BanR(a, b, c);
		}
	},
	BORI {
		@Override
		public BorI create(int a, int b, int c) {
			return new BorI(a, b, c);
		}
	},
	BORR {
		@Override
		public BorR create(int a, int b, int c) {
			return new BorR(a, b, c);
		}
	},
	EQIR {
		@Override
		public EqIR create(int a, int b, int c) {
			return new EqIR(a, b, c);
		}
	},
	EQRI {
		@Override
		public EqRI create(int a, int b, int c) {
			return new EqRI(a, b, c);
		}
	},
	EQRR {
		@Override
		public EqRR create(int a, int b, int c) {
			return new EqRR(a, b, c);
		}
	},
	GTIR {
		@Override
		public GtIR create(int a, int b, int c) {
			return new GtIR(a, b, c);
		}
	},
	GTRI {
		@Override
		public GtRI create(int a, int b, int c) {
			return new GtRI(a, b, c);
		}
	},
	GTRR {
		@Override
		public GtRR create(int a, int b, int c) {
			return new GtRR(a, b, c);
		}
	},
	MULI {
		@Override
		public MulI create(int a, int b, int c) {
			return new MulI(a, b, c);
		}
	},
	MULR {
		@Override
		public MulR create(int a, int b, int c) {
			return new MulR(a, b, c);
		}
	},
	SETI {
		@Override
		public SetI create(int a, int b, int c) {
			return new SetI(a, b, c);
		}
	},
	SETR {
		@Override
		public SetR create(int a, int b, int c) {
			return new SetR(a, b, c);
		}
	};

	public Op create(int[] instruction) {
		Objects.requireNonNull(instruction, "instruction must not be null.");
		if (instruction.length == 3) {
			return create(instruction[0], instruction[1], instruction[2]);
		} else if (instruction.length == 4) {
			return create(instruction[1], instruction[2], instruction[3]);
		} else {
			throw new IllegalArgumentException();
		}

	}

	public abstract Op create(int a, int b, int c);

}

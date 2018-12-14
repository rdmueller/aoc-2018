
public enum CartOrientation {

	RIGHT('>') {
		@Override
		public XY nextPosition(XY currentPosition) {
			return new XY(currentPosition.getX() + 1, currentPosition.getY());
		}

		@Override
		public CartOrientation nextOrientation(char symbol) {
			switch (symbol) {
			case '-':
				return RIGHT;
			case '\\':
				return DOWN;
			case '/':
				return UP;
			case '+':
				// TODO
				return RIGHT;
			default:
				throw new IllegalArgumentException();
			}
		}
	},
	DOWN('v') {
		@Override
		public XY nextPosition(XY currentPosition) {
			return new XY(currentPosition.getX(), currentPosition.getY() + 1);
		}

		@Override
		public CartOrientation nextOrientation(char symbol) {
			switch (symbol) {
			case '|':
				return CartOrientation.DOWN;
			case '\\':
				return RIGHT;
			case '/':
				return LEFT;
			case '+':
				// TODO
				return DOWN;
			default:
				throw new IllegalArgumentException();
			}
		}
	},
	LEFT('<') {
		@Override
		public XY nextPosition(XY currentPosition) {
			return new XY(currentPosition.getX() - 1, currentPosition.getY());
		}

		@Override
		public CartOrientation nextOrientation(char symbol) {
			switch (symbol) {
			case '-':
				return LEFT;
			case '\\':
				return UP;
			case '/':
				return DOWN;
			case '+':
				// TODO
				return LEFT;
			default:
				throw new IllegalArgumentException();
			}
		}
	},
	UP('^') {
		@Override
		public XY nextPosition(XY currentPosition) {
			return new XY(currentPosition.getX(), currentPosition.getY() - 1);
		}

		@Override
		public CartOrientation nextOrientation(char symbol) {
			switch (symbol) {
			case '|':
				return UP;
			case '\\':
				return LEFT;
			case '/':
				return RIGHT;
			case '+':
				// TODO
				return UP;
			default:
				throw new IllegalArgumentException();
			}
		}
	};

	private final char symbol;

	private CartOrientation(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	public abstract XY nextPosition(XY currentPosition);

	public abstract CartOrientation nextOrientation(char symbol);
}

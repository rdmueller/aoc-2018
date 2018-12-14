
public enum CartTurn {

	LEFT {

		@Override
		public CartOrientation nextOrientation(char symbol, CartOrientation currentOrientation) {
			if (symbol != '+') {
				return currentOrientation;
			}
			switch (currentOrientation) {
			case LEFT:
				return CartOrientation.DOWN;
			case DOWN:
				return CartOrientation.RIGHT;
			case RIGHT:
				return CartOrientation.UP;
			case UP:
				return CartOrientation.LEFT;
			default:
				throw new IllegalArgumentException();
			}
		}

		@Override
		public CartTurn nextTurn(char symbol) {
			switch (symbol) {
			case '+':
				return STRAIGHT;
			default:
				return this;
			}
		}
	},
	STRAIGHT {
		@Override
		public CartOrientation nextOrientation(char symbol, CartOrientation currentOrientation) {
			return currentOrientation;
		}
		
		@Override
		public CartTurn nextTurn(char symbol) {
			switch (symbol) {
			case '+':
				return RIGHT;
			default:
				return this;
			}
		}
	},
	RIGHT {
		
		@Override
		public CartOrientation nextOrientation(char symbol, CartOrientation currentOrientation) {
			if (symbol != '+') {
				return currentOrientation;
			}
			switch (currentOrientation) {
			case LEFT:
				return CartOrientation.UP;
			case DOWN:
				return CartOrientation.LEFT;
			case RIGHT:
				return CartOrientation.DOWN;
			case UP:
				return CartOrientation.RIGHT;
			default:
				throw new IllegalArgumentException();
			}
		}
		@Override
		public CartTurn nextTurn(char symbol) {
			switch (symbol) {
			case '+':
				return LEFT;
			default:
				return this;
			}
		}
	};

	public abstract CartOrientation nextOrientation(char symbol, CartOrientation currentOrientation);

	public abstract CartTurn nextTurn(char symbol);
}


public final class SquareInch {

	public final int x;
	public final int y;

	public SquareInch(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		SquareInch that = (SquareInch) object;
		return (this.x == that.x) && (this.y == that.y);
	}

	@Override
	public String toString() {
		return "@ " + this.x + "," + this.y;
	}
}

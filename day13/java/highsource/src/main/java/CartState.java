import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CartState {

	private XY currentPosition;
	private CartOrientation currentOrientation;
	private CartTurn currentTurn;

	public CartState(int x, int y, CartOrientation orientation, CartTurn currentTurn) {
		this.currentPosition = new XY(x, y);
		this.currentOrientation = orientation;
		this.currentTurn = currentTurn;
	}
	
	public CartState(XY currentPosition, CartOrientation currentOrientation, CartTurn currentTurn) {
		this.currentPosition = currentPosition;
		this.currentOrientation = currentOrientation;
		this.currentTurn = currentTurn;
	}

	public XY getCurrentPosition() {
		return currentPosition;
	}

	public CartOrientation getCurrentOrientation() {
		return currentOrientation;
	}
	
	public CartState nextState(TracksMap tracksMap) {
		final XY nextPosition = this.currentOrientation.nextPosition(this.currentPosition);
		final char nextSymbol = tracksMap.get(nextPosition);
		final CartOrientation nextOrientation = 
				this.currentTurn.nextOrientation(nextSymbol, this.currentOrientation.nextOrientation(nextSymbol));
		final CartTurn nextTurn = this.currentTurn.nextTurn(nextSymbol);
		return new CartState(nextPosition, nextOrientation, nextTurn);
	}
}

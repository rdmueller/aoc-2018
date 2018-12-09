import java.util.ArrayList;
import java.util.List;

public class MarbleCircle {
	
	private final List<Marble> marbles;
	
	private int currentMarbleIndex;
	
	public MarbleCircle() {
		this.marbles = new ArrayList<>();
		currentMarbleIndex = -1;
	}
	
	public void place(Marble marble) {
		if (marbles.size() == 0) {
			if (currentMarbleIndex != -1 ) {
				throw new IllegalStateException();
			}
			if (marble.getValue() != 0) {
				throw new IllegalArgumentException("First marble placed in the circle must have the value [0].");
			}
			this.currentMarbleIndex = marbles.size();
			this.marbles.add(marble);
		}
		else if (marbles.size() == 1) {
			if (currentMarbleIndex != 0 ) {
				throw new IllegalStateException();
			}
			if (marble.getValue() != 1) {
				throw new IllegalArgumentException("Second marble placed in the circle must have the value [1].");
			}
			this.currentMarbleIndex = marbles.size();
			this.marbles.add(marble);
		}
		else if (marble.isSpecial()) {
			// Do not place the marble
			final int nextMarbleIndex = ((currentMarbleIndex - 7) % marbles.size());
			Marble marble7 = this.marbles.remove(nextMarbleIndex);
			this.currentMarbleIndex = nextMarbleIndex;
		}
		else {
			final int nextMarbleIndex = ((currentMarbleIndex + 1) % marbles.size()) + 1;
			this.marbles.add(nextMarbleIndex, marble);
			this.currentMarbleIndex = nextMarbleIndex;
		}
	}

	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (int index = 0; index < marbles.size(); index++) {
			final Marble marble = marbles.get(index);
			if (index > 0) {
				sb.append(' ');
			}
			if (index == currentMarbleIndex) {
				sb.append('(');
			}
			sb.append(marble);
			if (index == currentMarbleIndex) {
				sb.append(')');
			}
		}
		return sb.toString();
	}
}

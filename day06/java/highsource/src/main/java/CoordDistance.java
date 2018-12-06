import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CoordDistance {

	private final char id;
	private final int x;
	private final int y;
	private final int d;

	@Override
	public String toString() {
		return id + ": " + x + ", " + y + " ... " + d;
	}

}

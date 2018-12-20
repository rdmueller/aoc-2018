package expr;

import java.util.Collections;
import java.util.Set;

import facility.Facility;
import facility.XY;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Step extends Expr {
	private final char direction;

	@Override
	public Set<XY> traceFrom(XY from, Facility facility) {
		int fromX = from.getX();
		int fromY = from.getY();
		final int toX;
		final int toY;
		switch (direction) {
		case 'N':
			toX = fromX;
			toY = fromY - 1;
			break;
		case 'E':
			toX = fromX + 1;
			toY = fromY;
			break;
		case 'W':
			toX = fromX - 1;
			toY = fromY;
			break;
		case 'S':
			toX = fromX;
			toY = fromY + 1;
			break;
		default:
			throw new UnsupportedOperationException();
		}

		XY to = new XY(toX, toY);

		facility.step(from, to);

		return Collections.singleton(to);
	}
}

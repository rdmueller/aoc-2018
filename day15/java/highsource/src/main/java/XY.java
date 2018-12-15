import java.util.Comparator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class XY {

	public static Comparator<XY> COMPARATOR = Comparator.comparingInt(XY::getY).thenComparingInt(XY::getX);

	private final int x;
	private final int y;

}

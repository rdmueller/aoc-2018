import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Marble {

	private static final int SPECIAL_MARBLE_VALUE_DIVISOR = 23;
	private final int value;

	public boolean isSpecial() {
		return (value % SPECIAL_MARBLE_VALUE_DIVISOR) == 0;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}

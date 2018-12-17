import java.util.Arrays;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import register.Registers;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Sample {
	
	private final Registers before;
	private final int[] instruction;
	private final Registers after;
	
	@Override
	public String toString() {
		return before + " >>>> " + Arrays.toString(instruction) + " >>>> " + after; 
	}
}

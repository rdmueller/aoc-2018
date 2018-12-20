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
public class Stand extends Expr {

	@Override
	public Set<XY> traceFrom(XY from, Facility facility) {
		return Collections.singleton(from);
	}
}

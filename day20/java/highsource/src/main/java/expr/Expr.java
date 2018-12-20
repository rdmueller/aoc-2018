package expr;

import java.util.Set;

import facility.Facility;
import facility.XY;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class Expr {

	public abstract Set<XY> traceFrom(XY start, Facility facility);
}

package expr;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import facility.Facility;
import facility.XY;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Branch extends Expr {

	private final List<Expr> options;

	public Branch(Expr... options) {
		this(Arrays.asList(options));
	}

	@Override
	public Set<XY> traceFrom(XY start, Facility facility) {
		return options.stream().map(option -> option.traceFrom(start, facility)).flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

}

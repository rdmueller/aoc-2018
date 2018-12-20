package expr;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
public class Route extends Expr {

	private final List<Expr> steps;

	public Route(Expr... steps) {
		this(Arrays.asList(steps));
	}

	@Override
	public Set<XY> traceFrom(XY from, Facility facility) {

		Set<XY> tos = Collections.singleton(from);

		for (Expr currentStep : steps) {
			tos = tos.stream().map(currentFrom -> currentStep.traceFrom(currentFrom, facility))
					.flatMap(Collection::stream).collect(Collectors.toSet());
		}

		return tos;
	}

}

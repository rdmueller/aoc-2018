package expr;

import java.util.Arrays;
import java.util.List;

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

}

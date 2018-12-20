package expr;

import java.util.Arrays;
import java.util.List;

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

}

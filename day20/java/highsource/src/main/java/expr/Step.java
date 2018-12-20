package expr;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString
public class Step extends Expr {
	private final char direction;
}

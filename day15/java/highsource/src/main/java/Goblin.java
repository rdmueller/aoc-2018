import java.util.Objects;

public class Goblin extends Unit {

	public Goblin(XY position) {
		super('G', Objects.requireNonNull(position, "position must not be null"));
	}

}

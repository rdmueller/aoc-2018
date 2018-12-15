import java.util.Objects;

public class Elf extends Unit {

	public Elf(XY position) {
		super('E', Objects.requireNonNull(position, "position must not be null"));
	}
}

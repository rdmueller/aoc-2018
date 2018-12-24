package army;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Army {

	private final List<Group> groups = new ArrayList<>();

	public void add(Group group) {
		Objects.requireNonNull(group, "group must not be null.");
		this.groups.add(group);
	}
}

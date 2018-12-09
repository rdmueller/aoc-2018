import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Player {

	private final int id;

	private long score = 0;

	public void place(int currentMarble, MarbleCircle circle) {
		this.score += circle.place(currentMarble);
	}
}

import static java.util.stream.Collectors.toCollection;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class GameProcess {
	
	private final MarbleCircle circle = new MarbleCircle();
	private final NavigableSet<Marble> remainingMarbles;
	
	public GameProcess(int numberOfPlayers, int lastMarbleValue) {
		remainingMarbles = IntStream.range(0, lastMarbleValue + 1).mapToObj(Marble::new).collect(
				toCollection(() ->new TreeSet<>(Comparator.comparingInt(Marble::getValue))));
	}
	
	

}

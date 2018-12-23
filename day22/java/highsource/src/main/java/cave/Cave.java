package cave;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Cave {

	private static final int WALK_DISTANCE = 1;
	private static final int CHANGE_DISTANCE = 7;
	private static final BigInteger REGION_TYPE_DIVISOR = BigInteger.valueOf(3);
	private static final BigInteger GEOLOGIC_INDEX_FACTOR_X = BigInteger.valueOf(16807);
	private static final BigInteger GEOLOGIC_INDEX_FACTOR_Y = BigInteger.valueOf(48271);
	private static final BigInteger EROSION_LEVEL_DIVIDER = BigInteger.valueOf(20183);

	private final int depth;
	private final BigInteger bigDepth;
	private final int targetX;
	private final int targetY;
	private final BigInteger[][] geologicIndexCache;
	private final int width;
	private final int height;

	public Cave(int depth, int targetX, int targetY) {
		this.depth = depth;
		this.height = depth + 51;
		this.width = targetX + 51;
		this.targetX = targetX;
		this.bigDepth = BigInteger.valueOf(depth);
		this.targetY = targetY;
		this.geologicIndexCache = new BigInteger[height][width];
		initGeologicIndexCache();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void initGeologicIndexCache() {
		for (int iy = 0; iy < height; iy++) {
			for (int ix = 0; ix < width; ix++) {
				geologicIndexCache[iy][ix] = doCalculateGeologicIndex(ix, iy);
			}
		}
	}

	public RegionType calculateRegionType(int x, int y) {
		final int regionTypeIndex = calculateErosionLevel(x, y).mod(REGION_TYPE_DIVISOR).intValue();
		return RegionType.values()[regionTypeIndex];
	}

	public BigInteger calculateErosionLevel(int x, int y) {
		final BigInteger geologicIndex = calculateGeologicIndex(x, y);
		return geologicIndex.add(bigDepth).mod(EROSION_LEVEL_DIVIDER);
	}

	public BigInteger calculateGeologicIndex(int x, int y) {

		BigInteger geologicIndex = geologicIndexCache[y][x];
		if (geologicIndex == null) {
			geologicIndex = doCalculateGeologicIndex(x, y);
			geologicIndexCache[y][x] = geologicIndex;
		}
		return geologicIndex;
	}

	private BigInteger doCalculateGeologicIndex(int x, int y) {
		if (x == 0 && y == 0) {
			return BigInteger.ZERO;
		} else if (x == targetX && y == targetY) {
			return BigInteger.ZERO;
		} else if (y == 0) {
			return GEOLOGIC_INDEX_FACTOR_X.multiply(BigInteger.valueOf(x));
		} else if (x == 0) {
			return GEOLOGIC_INDEX_FACTOR_Y.multiply(BigInteger.valueOf(y));
		} else {
			BigInteger left = calculateErosionLevel(x - 1, y);
			BigInteger up = calculateErosionLevel(x, y - 1);
			return left.multiply(up);
		}
	}

	public Integer walk() {
		return walkTo(new State(this.targetX, this.targetY, Tool.TORCH));
	}

	public Integer walkTo(State finalState) {

		final State initialState = new State(0, 0, Tool.TORCH);

		final Map<State, Integer> distanceByState = new HashMap<>();
		final Map<State, State> previousByState = new HashMap<>();
		distanceByState.put(initialState, 0);
		final Set<State> statesToVisit = new HashSet<>();

//				new TreeSet<>(Comparator.<State>comparingInt(s -> {
//			return Math.abs(s.getX() - finalState.getX()) + Math.abs(s.getY() - finalState.getY());
//		}));

		statesToVisit.add(initialState);

		int targetDistance = Integer.MAX_VALUE;

		while (!statesToVisit.isEmpty()) {

			System.out.println("Final state is reachable with distance " + targetDistance + ", states visited:" + distanceByState.size() + ", states left:" + statesToVisit.size());

			final State currentState = statesToVisit.stream().min(Comparator.<State>comparingInt(s -> {
				return Math.abs(s.getX() - finalState.getX()) + Math.abs(s.getY() - finalState.getY());
			})).orElseThrow(IllegalStateException::new);

//			final State currentState = statesToVisit.iterator().next();
			statesToVisit.remove(currentState);
			final int currentDistance = distanceByState.get(currentState);

			if (finalState.equals(currentState)) {
				if (currentDistance < targetDistance) {
					System.out.println("Final state is reachable with distance " + currentDistance + ".");
					targetDistance = currentDistance;
				}
			}

			if (currentDistance > targetDistance) {
				continue;
			}

			final Collection<State> walk = currentState.walk(this);

			for (State neighbour : walk) {
				final int neighbourDistance = distanceByState.getOrDefault(neighbour, Integer.MAX_VALUE);
				final int newDistance = currentDistance + WALK_DISTANCE;
				if (newDistance < neighbourDistance) {
					distanceByState.put(neighbour, newDistance);
//					System.out.println("New distance to " + neighbour + " is " + newDistance);
					previousByState.put(neighbour, currentState);
					statesToVisit.add(neighbour);
				}
			}

			final Collection<State> change = currentState.change(this);
			for (State neighbour : change) {
				final int neighbourDistance = distanceByState.getOrDefault(neighbour, Integer.MAX_VALUE);
				final int newDistance = currentDistance + CHANGE_DISTANCE;
				if (newDistance < neighbourDistance) {
					distanceByState.put(neighbour, newDistance);
					previousByState.put(neighbour, currentState);
					statesToVisit.add(neighbour);
				}
			}
		}

		final Integer resultingDistance = distanceByState.get(finalState);

		State s = finalState;

		do {
			System.out.println(s);
			s = previousByState.get(s);
		} while (s != null);

		return resultingDistance;
	}
}
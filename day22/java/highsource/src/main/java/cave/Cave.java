package cave;

import java.math.BigInteger;

public class Cave {

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
		this.height = depth + 100;
		this.width = 1000;
		this.targetX = targetX;
		this.bigDepth = BigInteger.valueOf(depth);
		this.targetY = targetY;
		this.geologicIndexCache = new BigInteger[height][width];
		initGeologicIndexCache();
	}

	private void initGeologicIndexCache() {
		for (int iy = 0; iy < height; iy++) {
			for (int ix = 0; ix < width; ix++) {
				geologicIndexCache[iy][ix] = doCalculateGeologicIndex(ix, iy);
			}
			System.out.println("> " + iy);
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
}
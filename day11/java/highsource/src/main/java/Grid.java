public class Grid {

	public static final XYSize findMax(int serialNumber) {

		int maxTotalPower = Integer.MIN_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int maxSize = Integer.MIN_VALUE;

		for (int y = 1; y <= 300; y++) {
			for (int x = 1; x <= 300; x++) {
				final int mxSizeX = 300 - x + 1;
				final int mxSizeY = 300 - y + 1;
				final int mxSize = Math.min(mxSizeX, mxSizeY);
				for (int size = 1; size <= mxSize; size++) {
					int totalPower = totalPowerAt(serialNumber, x, y, size);
					if (totalPower > maxTotalPower) {
						maxTotalPower = totalPower;
						maxX = x;
						maxY = y;
						maxSize = size;
					}
				}
			}
		}
		return new XYSize(maxX, maxY, maxSize);
	}

	public static final XY findMax(int serialNumber, int w) {

		int maxTotalPower = Integer.MIN_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (int y = 1; y <= 300 - w + 1; y++) {
			for (int x = 1; x <= 300 - w + 1; x++) {
				int totalPower = totalPowerAt(serialNumber, x, y, w);
				if (totalPower > maxTotalPower) {
					maxTotalPower = totalPower;
					maxX = x;
					maxY = y;
				}
			}
		}
		return new XY(maxX, maxY);
	}

	public static final int totalPowerAt(int serialNumber, int x, int y, int w) {

		int totalPower = 0;
		for (int j = 0; j < w; j++) {
			for (int i = 0; i < w; i++) {
				totalPower += levelAt(serialNumber, x + i, y + j);
			}
		}
		return totalPower;
	}

	public static final int levelAt(int serialNumber, int x, int y) {
		if (x < 1 || x > 300) {
			throw new IllegalArgumentException("Invalid x [" +  x + "].");
		}
		if (y < 1 || y > 300) {
			throw new IllegalArgumentException("Invalid y [" +  y + "].");
		}

		final int rackId = x + 10;
		final int powerLevelStartsAt = rackId * y;
		final int withAddedSerialNumber = powerLevelStartsAt + serialNumber;
		final int multipliedByRackId = withAddedSerialNumber * rackId;
		final int hundredsDigit = (multipliedByRackId % 1000) / 100;
		final int withSubstracted5 = hundredsDigit - 5;
		return withSubstracted5;
	}
}

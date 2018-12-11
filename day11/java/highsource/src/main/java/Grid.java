public class Grid {

	public static final int levelAt(int serialNumber, int x, int y) {
		final int rackId = x + 10;
		final int powerLevelStartsAt = rackId * y;
		final int withAddedSerialNumber = powerLevelStartsAt + serialNumber;
		final int multipliedByRackId = withAddedSerialNumber * rackId;
		final int hundredsDigit = (multipliedByRackId % 1000) / 100;
		final int withSubstracted5 = hundredsDigit - 5;
		return withSubstracted5;
	}
}

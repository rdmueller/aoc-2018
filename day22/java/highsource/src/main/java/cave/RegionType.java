package cave;

public enum RegionType {

	ROCKY(0), WET(1), NARROW(2);

	private final int riskLevel;

	private RegionType(int riskLevel) {
		this.riskLevel = riskLevel;
	}

	public int getRiskLevel() {
		return riskLevel;
	}
}

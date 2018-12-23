package cave;

public enum Tool {

	TORCH {
		@Override
		public boolean suitableFor(RegionType regionType) {
			return regionType == RegionType.ROCKY ||
					regionType == RegionType.NARROW;
		}
	},
	CLIMBING_GEAR{
		@Override
		public boolean suitableFor(RegionType regionType) {
			return regionType == RegionType.ROCKY ||
					regionType == RegionType.WET;
		}
	},
	NEITHER {
		@Override
		public boolean suitableFor(RegionType regionType) {
			return regionType == RegionType.WET || 
					regionType == RegionType.NARROW;
		}
	};
	
	
	public abstract boolean suitableFor(RegionType regionType);
}

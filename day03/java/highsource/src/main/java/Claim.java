import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Claim {

	public final int id;
	public final int left;
	public final int top;
	public final int width;
	public final int height;

	public Claim(int id, int left, int top, int width, int height) {
		this.id = id;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + id;
		result = prime * result + left;
		result = prime * result + top;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Claim that = (Claim) object;
		return (this.id == that.id) && (this.left == that.left) && (this.top == that.top) && (this.width == that.width)
				&& (this.height == that.height);
	}

	@Override
	public String toString() {
		return "#" + this.id + " @ " + this.left + "," + this.top + ": " + this.width + "x" + this.height;
	}

	public List<SquareInch> area() {
		final List<SquareInch> area = new ArrayList<>();

		for (int x = left; x < left + width; x++) {
			for (int y = top; y < top + height; y++) {
				area.add(new SquareInch(x, y));
			}
		}
		return area;
	}

	private static final String CLAIM_REGEX = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$";
	private static final Pattern PATTERN = Pattern.compile(CLAIM_REGEX);

	public static Claim parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");
		final Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches() || matcher.groupCount() != 5) {
			throw new IllegalArgumentException();
		} else {
			final String idStr = matcher.group(1);
			final String leftStr = matcher.group(2);
			final String topStr = matcher.group(3);
			final String widthStr = matcher.group(4);
			final String heightStr = matcher.group(5);

			return new Claim(Integer.parseInt(idStr), Integer.parseInt(leftStr), Integer.parseInt(topStr),
					Integer.parseInt(widthStr), Integer.parseInt(heightStr));
		}
	}
}
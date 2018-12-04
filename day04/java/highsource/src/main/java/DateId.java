import java.time.LocalDate;
import java.util.Objects;

public final class DateId {

	public final LocalDate date;
	public final int id;

	public DateId(LocalDate date, int id) {
		this.date = date;
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.date, this.id);
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
		final DateId that = (DateId) object;
		return Objects.equals(this.date, that.date) && this.id == that.id;
	}
}

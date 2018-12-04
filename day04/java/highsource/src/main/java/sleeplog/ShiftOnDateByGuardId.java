package sleeplog;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class ShiftOnDateByGuardId {

	private final LocalDate date;
	private final int guardId;
}

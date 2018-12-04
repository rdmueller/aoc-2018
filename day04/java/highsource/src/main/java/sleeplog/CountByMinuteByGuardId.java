package sleeplog;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CountByMinuteByGuardId {

	private final int guardId;
	private final int minute;
	private final long count;
}

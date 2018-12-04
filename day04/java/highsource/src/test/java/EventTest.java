import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

import event.BeginsShiftEvent;
import event.Event;
import event.FallsAsleepEvent;
import event.WakesUpEvent;

public class EventTest {

	@Test
	public void parsesBeginsShiftEvent() {
		assertThat(
				Event.parse("[1518-11-01 23:58] Guard #99 begins shift")).
		
		isEqualTo(new BeginsShiftEvent(LocalDateTime.of(1518, 11, 01, 23, 58), 99));
	}

	@Test
	public void parsesFallsAsleepEvent() {
		assertThat(
				Event.parse("[1518-11-02 00:40] falls asleep")).
		
		isEqualTo(new FallsAsleepEvent(LocalDateTime.of(1518, 11, 02, 00, 40)));
	}
	
	@Test
	public void parsesWakesUpEvent() {
		assertThat(
				Event.parse("[1518-11-02 00:50] wakes up")).
		
		isEqualTo(new WakesUpEvent(LocalDateTime.of(1518, 11, 02, 00, 50)));
	}
	
}

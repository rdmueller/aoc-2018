package nanobot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class NanobotTest {

	@Test
	public void parses() {

		assertThat(Nanobot.parse("pos=<-133276094,54755793,-23263741>, r=67312129"))
				.isEqualTo(new Nanobot(-133276094, 54755793, -23263741, 67312129));
	}
}

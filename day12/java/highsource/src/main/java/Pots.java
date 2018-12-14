import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Pots {
	
	private static final int BUFFER = 300;

	private final char[] state;

	@Override
	public String toString() {
		return new String(state);
	}

	public Pots apply(List<Note> notes) {

		char[] nextState = new char[state.length];
		Arrays.fill(nextState, '.');

		for (int i = 2; i < state.length - 2; i++) {
			final int atIndex = i;
			notes.stream().filter(note -> note.matchesAt(this.state, atIndex)).findAny()
					.ifPresent(note -> nextState[atIndex] = note.getNext());
		}
		return new Pots(nextState);
	}
	
	public int value() {
		int value = 0;
		for (int index = 0; index < this.state.length; index ++) {
			if (this.state[index] == '#') {
				value += (index - BUFFER);
			}
		}
		return value;
	}

	public static Pots parse(String str) {
		Objects.requireNonNull(str, "str must not be null");
		char[] st = str.toCharArray();

		char[] state = new char[BUFFER + st.length + BUFFER];
		Arrays.fill(state, '.');
		System.arraycopy(st, 0, state, BUFFER, st.length);

		return new Pots(state);
	}
}

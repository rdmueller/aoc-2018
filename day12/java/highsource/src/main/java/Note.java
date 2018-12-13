import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Note {
	
	private final char[] state;
	private final char next;

	public Note(String state, char next) {
		this(state.toCharArray(), next);
	}
	
	public boolean matchesAt(char[] s, int i) {
		
		int halfLength = state.length / 2;
		
		boolean matches = true;
		for (int index = - halfLength; matches && (index <= halfLength); index ++) {
			matches &= (state[halfLength + index] == s[i + index]);
		}
		return matches;
	}
	
	@Override
	public String toString() {
		return new String(state) + " => " + next;
	}

	private static final String REGEX = "^([\\.|#]+) => ([\\.|#])$";
	private static final Pattern PATTERN = Pattern.compile(REGEX);
	
	public static Note parse(String str) {
		Objects.requireNonNull(str, "str must not be null");
		Matcher matcher = PATTERN.matcher(str);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(str);
		}
		
		return new Note(matcher.group(1), matcher.group(2).charAt(0));
	}


}

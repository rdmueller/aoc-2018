import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			String initialStateString = reader.readLine().substring(15);

			final Pots pots = Pots.parse(initialStateString);
			System.out.println(pots);

			reader.readLine();

			final List<Note> notes = new ArrayList<>(32);
			for (String line; (line = reader.readLine()) != null;) {
				final Note note = Note.parse(line);
				notes.add(note);
				System.out.println(note);
			}

			{
				// Part1
				Pots currentPots = pots;
				for (int step = 0; step < 200; step++) {
					System.out.print(StringUtils.leftPad(Integer.toString(step + 1), 2) + ":");
					currentPots = currentPots.apply(notes);
					System.out.print(" === ");
					System.out.println(currentPots.value());
				}

				// Part2
				long value200 = currentPots.value();
				long value201 = currentPots.apply(notes).value();

				int delta = (int) (value201 - value200);

				long value50000000000 = value200 + (50000000000L - 200) * delta;

				System.out.println(50000000000L + ": === " + value50000000000);
			}
		}
	}
}
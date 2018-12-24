import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import army.Group;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("input.txt")))) {

			final List<Group> immuneSystem = new ArrayList<>();
			final List<Group> infection = new ArrayList<>();

			List<Group> currentArmy = null;

			for (String line; (line = reader.readLine()) != null;) {
				if ("Immune System:".equals(line)) {
					currentArmy = immuneSystem;
				} else if ("Infection:".equals(line)) {
					currentArmy = infection;
				} else if ("".equals(line)) {
					// Skip
				} else {
					currentArmy.add(Group.parse(line));
				}
			}
		}
	}
}
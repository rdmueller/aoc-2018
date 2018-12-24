import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import army.Army;
import army.Group;

public class Solution {

	public static void main(String[] args) throws IOException {

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Solution.class.getResourceAsStream("test0.txt")))) {

			final Army immuneSystem = new Army();
			final Army infection = new Army();

			Army currentArmy = null;

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

			System.out.println("Immune system:");
			System.out.println(immuneSystem);
			System.out.println("Infection:");
			System.out.println(infection);
		}
	}
}
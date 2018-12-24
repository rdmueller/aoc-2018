package army;

import static army.DamageType.bludgeoning;
import static army.DamageType.cold;
import static army.DamageType.fire;
import static army.DamageType.radiation;
import static army.DamageType.slashing;
import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

public class GroupTest {

	@Test
	public void parses() {

		assertThat(Group.parse(
				//
				"585 units "
						//
						+ "each with 7536 hit points "
						//
						+ "("
						//

						+ "immune to fire, radiation, cold; "
						//

						+ "weak to bludgeoning) "
						//
						+ "with an attack that does 116 "
						//
						+ "radiation damage "
						//
						+ "at initiative 3")).isEqualTo(new Group.GroupBuilder().
		//
								numberOfUnits(585).
								//
								hitPointsPerUnit(7536).
								//
								immuneTo(new HashSet<>(Arrays.asList(fire, radiation, cold))).
								//
								weakTo(Collections.singleton(bludgeoning)).
								//
								attackHitPoints(116).
								//
								attackDamageType(radiation).
								//
								initiative(3).build());

		assertThat(Group.parse(
				"3247 units each with 8560 hit points (immune to radiation) with an attack that does 25 bludgeoning damage at initiative 13"))
						.isEqualTo(new Group.GroupBuilder().
						//
								numberOfUnits(3247).
								//
								hitPointsPerUnit(8560).
								//
								immuneTo(new HashSet<>(Arrays.asList(radiation))).
								//
								weakTo(Collections.emptySet()).
								//
								attackHitPoints(25).
								//
								attackDamageType(bludgeoning).
								//
								initiative(13).build());

		assertThat(Group.parse(
				"8629 units each with 1865 hit points (weak to radiation) with an attack that does 1 bludgeoning damage at initiative 20"))
						.isEqualTo(new Group.GroupBuilder().
						//
								numberOfUnits(8629).
								//
								hitPointsPerUnit(1865).
								//
								immuneTo(Collections.emptySet()).
								//
								weakTo(Collections.singleton(radiation)).
								//
								attackHitPoints(1).
								//
								attackDamageType(bludgeoning).
								//
								initiative(20).build());

		assertThat(Group.parse(
				"4126 units each with 53542 hit points with an attack that does 21 slashing damage at initiative 7"))
						.isEqualTo(new Group.GroupBuilder().
						//
								numberOfUnits(4126).
								//
								hitPointsPerUnit(53542).
								//
								immuneTo(emptySet()).
								//
								weakTo(emptySet()).
								//
								attackHitPoints(21).
								//
								attackDamageType(slashing).
								//
								initiative(7).build());
	}

	@Test
	public void effectivePower() {
		assertThat(Group.parse(
				"18 units each with 729 hit points (weak to fire; immune to cold, slashing) with an attack that does 8 radiation damage at initiative 10")
				.getEffectivePower()).isEqualTo(144);

	}
	
	@Test
	public void calculateDamage() {
		Group group1 = Group.parse("17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2");
		Group group2 = Group.parse("4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4");
		Group group3 = Group.parse("4485 units each with 2961 hit points (immune to fire; weak to cold) with an attack that does 12 slashing damage at initiative 4");
		Group group4 = Group.parse("4485 units each with 2961 hit points with an attack that does 12 slashing damage at initiative 4");
		assertThat(group1.calculateDamage(group2)).isEqualTo(153238);
		assertThat(group1.calculateDamage(group3)).isEqualTo(0);
		assertThat(group1.calculateDamage(group4)).isEqualTo(76619);

	}
	
}

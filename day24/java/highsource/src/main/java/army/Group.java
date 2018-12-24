package army;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class Group {

	private final int numberOfUnits;
	private final int hitPointsPerUnit;

	private final Set<DamageType> immuneTo;
	private final Set<DamageType> weakTo;

	private final int attackHitPoints;
	private final DamageType attackDamageType;

	private final int initiative;

	private static final String MAIN_REGEX = "^(\\d+) units each with (\\d+) hit points (\\([^\\)]+\\) )?with an attack that does (\\d+) ([^\\s]+) damage at initiative (\\d+)";

	private static final Pattern MAIN_PATTERN = Pattern.compile(MAIN_REGEX);

	private static final String IMMUNE_TO_REGEX = ".*immune to ([^;]+).*";

	private static final Pattern IMMUNE_TO_PATTERN = Pattern.compile(IMMUNE_TO_REGEX);

	private static final String WEAK_TO_REGEX = ".*weak to ([^;]+).*";

	private static final Pattern WEAK_TO_PATTERN = Pattern.compile(WEAK_TO_REGEX);

	public static Group parse(String str) {
		Objects.requireNonNull(str, "str must not be null.");

		Matcher mainMatcher = MAIN_PATTERN.matcher(str);
		if (!mainMatcher.matches()) {
			throw new IllegalArgumentException(str);
		} else {

			final int numberOfUnits = Integer.parseInt(mainMatcher.group(1));
			final int hitPointsPerUnit = Integer.parseInt(mainMatcher.group(2));
			final String immunitiesAndWeaknessesWithBrackets = mainMatcher.group(3);
			final int attackHitPoints = Integer.parseInt(mainMatcher.group(4));
			final DamageType attackDamageType = DamageType.valueOf(mainMatcher.group(5));
			final int initiative = Integer.parseInt(mainMatcher.group(6));

			final Set<DamageType> immuneTo;
			final Set<DamageType> weakTo;
			if (immunitiesAndWeaknessesWithBrackets != null) {
				final String immunitiesAndWeaknesses = immunitiesAndWeaknessesWithBrackets.substring(1,
						immunitiesAndWeaknessesWithBrackets.length() - 2);

				Matcher immuneToMatcher = IMMUNE_TO_PATTERN.matcher(immunitiesAndWeaknesses);

				if (immuneToMatcher.matches()) {
					String immuneToStr = immuneToMatcher.group(1);
					immuneTo = Arrays.asList(immuneToStr.split(",")).stream().map(String::trim).map(DamageType::valueOf)
							.collect(Collectors.toSet());
				} else {
					immuneTo = Collections.emptySet();
				}

				Matcher weakToMatcher = WEAK_TO_PATTERN.matcher(immunitiesAndWeaknesses);

				if (weakToMatcher.matches()) {
					String weakToStr = weakToMatcher.group(1);
					weakTo = Arrays.asList(weakToStr.split(",")).stream().map(String::trim).map(DamageType::valueOf)
							.collect(Collectors.toSet());
				} else {
					weakTo = Collections.emptySet();
				}
			} else {
				immuneTo = Collections.emptySet();
				weakTo = Collections.emptySet();
			}

			return new Group(numberOfUnits, hitPointsPerUnit, immuneTo, weakTo, attackHitPoints, attackDamageType,
					initiative);
		}
	}
}

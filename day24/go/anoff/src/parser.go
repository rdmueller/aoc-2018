package main

import (
	"regexp"
	"strings"
)

func createArmiesFromInput(input []string) (Army, Army) {
	immune := NewArmy("immune system")
	infection := NewArmy("infection")


	var curArmy *Army
	for _, line := range input {
		if strings.Contains(line, "Immune System:") {
			curArmy = &immune
		} else if strings.Contains(line, "Infection:") {
			curArmy = &infection
		} else if len(line) > 10 {
			m := parseLine(line)
			nums := StringSlice2IntSlice([]string{m["hp"], m["damage"], m["units"], m["initiative"]})
			u := Unit{hp: nums[0], damage: nums[1], attackType: m["attackType"], initiative: nums[3]}
			for _, w := range strings.Split(m["weakness"], ", ") {
				u.weaknesses = append(u.weaknesses, w)
			}
			for _, i := range strings.Split(m["immune"], ", ") {
				u.immunities = append(u.immunities, i)
			}
			g := NewGroup(u, nums[2])
			curArmy.addGroup(&g)
		}
	}

	return immune, infection
}

// parse for regex and create map of named subgroups
func parseLine(line string) map[string]string {
	m := make(map[string]string)
	re := regexp.MustCompile(`(?P<units>[0-9]+) units each with (?P<hp>[0-9]+) hit points \(?(weak to (?P<weakness>[a-zA-Z, ]+))?;?\s?(immune to (?P<immune>[a-zA-Z, ]+))?;?\s?(weak to (?P<weakness2>[a-zA-Z, ]+))?\)?\s?with an attack that does (?P<damage>[0-9]+) (?P<attackType>[a-zA-Z, ]+) damage at initiative (?P<initiative>[0-9]+)`)
	match := re.FindStringSubmatch(line)
	for i, name := range re.SubexpNames() {
		if i > 0 && i <= len(match) && len(match[i]) > 0 {
			m[name] = match[i]
		}
	}
	// fix the hack for order switch in weakness/immunity/weakness
	if val, exists := m["weakness2"]; exists {
		m["weakness"] = val
		delete(m, "weakness2")
	}
	return m
}
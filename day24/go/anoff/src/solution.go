package main

import (
	"fmt"
)

func main() {
	input := readInput("../input.txt")
	immune, infection := createArmiesFromInput(input)
	armies := []*Army{&immune, &infection}
	part1(armies)
}

func part1(armies []*Army) {
	for {
		turn(armies)
		done := false
		for _, a := range armies {
			activeGroups := 0
			for _, g := range a.groups {
				if g.units > 0 {
					activeGroups++
				}
			}
			if activeGroups == 0 {
				done = true
				fmt.Println("Faction", a.faction, "has no fighters left")
				var winner *Army
				if a == armies[0] {
					winner = armies[1]
				} else {
					winner = armies[0]
				}
				remainingUnits := 0
				for _, g := range winner.groups {
					remainingUnits += g.units
				}
				fmt.Println("Solution for part1:", remainingUnits)
				break
			}
		}
		if done {
			break
		}
	}
}
func turn(armies []*Army) {
	for _, a := range armies {
		fmt.Printf("\n%s army w/ %d groups\n", a.faction, len(a.groups))
		for _, g := range a.groups {
			fmt.Printf("\t%d with %d units\n", g.id, g.units)
		}
	}
	a1 := armies[0]
	a2 := armies[1]
	a1.planAttack(a2)
	a2.planAttack(a1)
	// for _, g := range append(a1.groups, a2.groups...) {
	// 	if g.units > 0 && g.target != nil {
	// 		fmt.Printf("..%s group %d planning to attack %s group %d for %d damage\n", g.faction, g.id, g.target.faction, g.target.id, g.damagePotential(g.target))
	// 	}
	// }
	strike(armies)
	a1.cleanup()
	a2.cleanup()
}

func strike(armies []*Army) {
	allGroups := append(armies[0].groups, armies[1].groups...)
	sortGroupsByInitiative(allGroups)
	for _, g := range allGroups {
		if g.units == 0 {
			// fmt.Println("Group already down to 0 units")
			continue
		} else if g.target == nil {
			// fmt.Println("Group has no target")
			continue
		}
		damage := g.damagePotential(g.target)
		unitLoss := damage / g.target.hp
		fmt.Printf("%s group %d (%s) attacks %s group %d (w: %s, i: %s) for %d damage, killing %d units\n", g.faction, g.id, g.attackType, g.target.faction, g.target.id, g.target.weaknesses, g.target.immunities, damage, unitLoss)
		g.target.units -= unitLoss
		if g.target.units < 0 {
			g.target.units = 0
		}
	}
}
func part2([]Army) {
	fmt.Println("Solution for part2:", 0)
}


/*
21645, too low
22232, too low

*/
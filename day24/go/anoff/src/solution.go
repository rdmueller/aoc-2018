package main

import (
	"fmt"
)

func main() {
	input := readInput("../input.txt")
	//part1(input)
	part2(input)
}

func part1(input []string) {
	immune, infection := createArmiesFromInput(input)
	armies := []*Army{&immune, &infection}
	i := 0
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
		i++
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
	// hack because allGroups = append(armies[0].groups, armies[1].groups...)
	//		resulted in the army.groups being rewritten
	var allGroups []*Group
	for _, a := range armies {
		for _, g := range a.groups {
			allGroups = append(allGroups, g)
		}
	}
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
		if unitLoss > g.target.units {
			unitLoss = g.target.units
		}
		fmt.Printf("%s group %d (%s) attacks %s group %d (w: %s, i: %s) for %d damage, killing %d units\n", g.faction, g.id, g.attackType, g.target.faction, g.target.id, g.target.weaknesses, g.target.immunities, damage, unitLoss)
		g.target.units -= unitLoss
	}
}
func part2(input []string) {
	solutionFound := false
	// TODO: for a fully automated search this needs to check if a fight goes "stale" i.e. neither army is taking damage this happens around 50~51
	for immuneBoost := 52; immuneBoost < 100; immuneBoost+=1 {
		immune, infection := createArmiesFromInput(input)
		for i := range immune.groups {
			immune.groups[i].damage += immuneBoost
		}
		armies := []*Army{&immune, &infection}
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
					fmt.Println("Fight ended:", remainingUnits, winner.faction, "for boost of ", immuneBoost)
					if winner.faction == "immune system" {
						solutionFound = true
					}
					break
				}
			}
			if done {
				break
			}
		}
		if solutionFound {
			fmt.Println("Solution for part2 found ^")
			break
		}
	}
}
		
		
		/*
21645, too low
22232, too low
22300, too high
22244, yusss
*/
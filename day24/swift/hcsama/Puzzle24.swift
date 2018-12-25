//
//  Puzzle24.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 24.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


private class Group: Comparable
{
    private static var idCount = 0
    var id: Int
    var nUnits: Int
    var hitPoints: Int
    var damage: Int
    var attackType: String
    var initiative: Int
    var weaknesses: [String]
    var immunities: [String]
    var armyName: String
    var enemy: Group?
    var power: Int { get { return nUnits * damage } }

    init(armyName arm: String, nUnits n: Int, hitPoints p: Int, damage d: Int, attackType a: String, initiative i: Int, weaknesses w: [String], immunities im: [String])
    {
        id = Group.idCount
        Group.idCount += 1
        enemy = nil
        armyName = arm
        nUnits = n
        hitPoints = p
        damage = d
        attackType = a
        initiative = i
        weaknesses = w
        immunities = im
    }

    func EffectiveDamage(_ d: Int, _ a: String) -> Int
    {
        if immunities.contains(a)
        {
            return 0
        }
        if weaknesses.contains(a)
        {
            return d * 2
        }
        return d
    }

    func Attack(_ d: Int, _ a: String) -> Bool
    {
        let old = nUnits
        nUnits = nUnits - EffectiveDamage(d, a)/hitPoints
        if nUnits < 0
        {
            nUnits = 0
        }
        return nUnits != old
    }

    static func < (lhs: Group, rhs: Group) -> Bool
    {
        return lhs.id < rhs.id
    }

    static func == (lhs: Group, rhs: Group) -> Bool
    {
        return lhs.id == rhs.id
    }

}

private func ReadArmies(_ groups: inout [Group], _ lines: inout [String], _ boost: Int)
{
    var armyName = ""
    for l in lines
    {
        let words = l.components(separatedBy: ["(", ")", ",", ";", " "])
        if words.count > 0 && words[0] != ""  // Skip blank lines
        {
            if let nUnits = Int(words[0])  // numeric lines are a group
            {
                let hitPoints = Int(words[4])!
                var weaknesses: [String] = []
                var immunities: [String] = []
                var curWord = 7
                var readingWeakness = true
                while words[curWord] != "with"
                {  // weaknesses and strengths
                    switch words[curWord]
                    {
                    case "weak":
                        readingWeakness = true
                    case "immune":
                        readingWeakness = false
                    case "to":
                        break
                    case "":
                        break
                    default:
                        if readingWeakness
                        {
                            weaknesses.append(words[curWord])
                        }
                        else
                        {
                            immunities.append(words[curWord])
                        }
                    }
                    curWord += 1
                }
                let damage = Int(words[curWord + 5])! + (armyName.starts(with: "Immune") ? boost : 0)
                let attackType = words[curWord + 6]
                let initiative = Int(words[curWord + 10])!
                groups.append(Group(armyName: armyName, nUnits: nUnits, hitPoints: hitPoints, damage: damage, attackType: attackType, initiative: initiative, weaknesses: weaknesses, immunities: immunities))
            }
            else  // new Army
            {
                armyName = words.joined().trimmingCharacters(in: [":"])
            }
        }
    }
}

private func SelectOpponents(_ groups: inout [Group])
{
    var availableGroups = groups
    groups.sort(by: {a, b in a.power == b.power ? a.initiative > b.initiative : a.power > b.power })
    for g in groups
    {
        g.enemy = nil
        let canAttack = availableGroups.filter({$0.armyName != g.armyName}).sorted(by:
        {
            let d1 = $0.EffectiveDamage(g.power, g.attackType)
            let d2 = $1.EffectiveDamage(g.power, g.attackType)
            if d1 == d2
            {
                if $0.power == $1.power
                {
                    return $0.initiative < $1.initiative
                }
                return $0.power < $1.power
            }
            return d1 < d2
        })
        if canAttack.count > 0 && canAttack.last!.EffectiveDamage(g.damage, g.attackType) > 0
        {
            g.enemy = canAttack.last
            availableGroups.remove(at: availableGroups.firstIndex(of: g.enemy!)!)
        }
    }
}

private func FightRound(_ groups: inout [Group]) -> Bool
{
    groups.sort(by: {$0.initiative > $1.initiative})
    var kills = false
    for g in groups
    {
        if g.nUnits > 0
        {
            if let e = g.enemy
            {
                kills = e.Attack(g.power, g.attackType) || kills
                g.enemy = nil
            }
        }
    }
    // remove empty units
    groups = groups.filter({$0.nUnits > 0})
    return kills
}

private func FightBattle(_ lines: inout [String], _ boost: Int) -> [String: Int]
{
    var groups: [Group] = []
    ReadArmies(&groups, &lines, boost)
    var round = 0
    while groups.reduce(into: Set<String>(), {s, g in s.insert(g.armyName)}).count > 1
    {
        round += 1
        SelectOpponents(&groups)
        if !FightRound(&groups)
        {
            break
        }
    }
    var retval: [String:Int] = [:]
    retval = groups.reduce(into:retval, {s, g in s[g.armyName] = 0})
    for a in retval.keys
    {
        retval[a] = groups.reduce(0, {$0 + ($1.armyName == a ? $1.nUnits : 0)})
    }
    return retval
}

func Puzzle24()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/immune.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/immune_test.txt")
    var lines: [String] = file.components(separatedBy: "\n").dropLast().filter {_ in true}
    var battleResult = FightBattle(&lines, 0)
    print("24-01:", battleResult)
    var boost = 1
    while battleResult.count > 1 || battleResult.first!.key.starts(with: "Infect")
    {
        boost = boost+1
        battleResult = FightBattle(&lines, boost)
        print("Boost", boost, battleResult)
    }
    print("24-02:", boost, battleResult)
}

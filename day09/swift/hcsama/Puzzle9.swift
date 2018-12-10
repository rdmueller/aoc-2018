//
//  Puzzle8.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 08.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


class Marble
{
    var value: Int
    var next: Marble?
    weak var prev: Marble?
    static var first: Marble? = nil

    init(_ v:Int, _ n: Marble?, _ p: Marble?)
    {
        next = n
        prev = p
        value = v
    }

    init(_ v: Int, _ m: Marble?)
    {
        value = v
        next = m?.next
        next?.prev = self
        m?.next = self
        prev = m
    }

    func remove() -> Marble?
    {
        prev?.next = next
        next?.prev = prev
        if Marble.first?.value == self.value
        {
            Marble.first = next
        }
        return next
    }
}


func RunMarblesList(_ nPlayers: Int, _ nMarbles: Int) -> Int64
{
    var curPlayer = 0
    var playerScores: [Int64] = Array(repeating: 0, count: nPlayers)
    var curMarble: Marble?

    Marble.first = Marble(0, nil, nil)
    Marble.first!.next = Marble.first
    Marble.first!.prev = Marble.first
    curMarble = Marble.first
    for i in 1 ..< nMarbles
    {
        if i % 23 == 0
        {
            playerScores[curPlayer] += Int64(i)
            curMarble = curMarble?.prev?.prev?.prev?.prev?.prev?.prev?.prev
            playerScores[curPlayer] += Int64(curMarble!.value)
            curMarble = curMarble?.remove()
        }
        else // Place marble
        {
            let hlp = Marble(i, curMarble?.next)
            curMarble = hlp
        }
        curPlayer = (curPlayer + 1) % nPlayers
    }
    return playerScores.max()!
}


func Puzzle9()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/marbles.txt")
    //let file = "9 players; last marble is worth 25 points: high score is 32\n"
    //let file = "10 players; last marble is worth 1618 points: high score is 8317\n"
    let words = file.components(separatedBy: "\n").dropLast()[0].components(separatedBy: " ")
    let nPlayers = Int(words[0])!
    let nMarbles = Int(words[6])! + 1

    print("09-01:", RunMarblesList(nPlayers, nMarbles))
    print("09-02:", RunMarblesList(nPlayers, (nMarbles-1)*100+1))
}

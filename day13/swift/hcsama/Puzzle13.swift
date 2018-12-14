//
//  Puzzle13.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 13.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


enum Turns
{
    case left
    case right
    case straight
}

class Cart
{
    private static var carts = 0
    let cartID = carts
    private let numDirections = 4
    var x: Int
    var y: Int
    var dir: Int
    private let turnings: [Turns] = [.left, .straight, .right]
    private var nextTurn = 0

    init(_ x0: Int, _ y0: Int, _ dir0: Int)
    {
        x = x0
        y = y0
        dir = dir0
        Cart.carts += 1
    }

    func SetNewPos()
    {
        switch dir
        {
        case 0:
            y -= 1
        case 1:
            x -= 1
        case 2:
            y += 1
        case 3:
            x += 1
        default:
            exit(-1)
        }
    }

    private func Turn(_ t: Turns)
    {
        switch t
        {
        case .left:
            dir = (dir + 1) % numDirections
        case .right:
            dir = dir == 0 ? numDirections-1 : dir-1
        case .straight:
            break
        }
    }

    func SetDirection(_ c: Character)
    {
        switch c
        {
        case "/":
            Turn([1,3].contains(dir) ? .left : .right)
        case "\\":
            Turn([1,3].contains(dir) ? .right : .left)
        case "+":
            Turn(turnings[nextTurn])
            nextTurn = (nextTurn+1) % turnings.count
        default:
            break
        }
    }

}

func Collision(_ carts: inout [Cart]) -> [Cart]
{
    for i in 0..<carts.count-1
    {
        for j in i+1..<carts.count
        {
            if carts[i].x == carts[j].x && carts[i].y == carts[j].y
            {
                return [carts[i], carts[j]]
            }
        }
    }
    return []
}

func Puzzle13()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/carts.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/carts_test.txt")
    var tracks:[[Character]] = file.components(separatedBy: "\n").dropLast().map() { $0.filter() {_ in true} }
    var carts:[Cart] = []

    // Find all carts
    let replacementTrack: [Character] = ["|", "-", "|", "-"]
    let cartArrows: [Character] = ["^", "<", "v", ">"]
    for y in 0..<tracks.count
    {
        for x in 0..<tracks[y].count
        {
            if cartArrows.contains(tracks[y][x])
            {
                let dir = cartArrows.firstIndex(of: tracks[y][x])!
                carts.append(Cart(x, y, dir))
                tracks[y][x] = replacementTrack[dir]
            }
        }
    }
    // Run the carts
    var tick = 0
    while carts.count > 1
    {
        tick += 1
        carts = carts.sorted(by: { c1, c2 in return c1.y < c2.y ? true : c1.y == c2.y ? c1.x < c2.x : false })
        for c in carts
        {
            c.SetNewPos()
            c.SetDirection(tracks[c.y][c.x])
            let d = Collision(&carts)
            if d.count > 0
            {
                print("13-01: tick cart x y", tick, d[0].cartID, d[0].x, d[0].y)
                carts.remove(at: carts.firstIndex(where: { $0.cartID == d[0].cartID })!)
                carts.remove(at: carts.firstIndex(where: { $0.cartID == d[1].cartID })!)
            }
        }
    }
    print("13-02: tick cart x y", tick, carts[0].cartID, carts[0].x, carts[0].y)
}

//
//  Puzzle14.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 14.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func Puzzle14()
{
    let recipeIterations = 440231
    let targetRecipe: [Int] = [4, 4, 0, 2, 3, 1]
    let lastTen = 10
    var recipes: [Int] = [3, 7]
    var elves: [Int] = [0 ,1]

    func RecipeIteration()
    {
        let newRecipe = elves.reduce(0, { r, e in r+recipes[e] })
        let tens:Int = newRecipe / 10
        let ones:Int = newRecipe % 10
        if tens > 0
        {
            recipes.append(tens)
            if ContainsRecipe()
            {
                return
            }
        }
        recipes.append(ones)
        for i in 0..<elves.count
        {
            elves[i] = (elves[i] + recipes[elves[i]] + 1) % recipes.count
        }
    }

    func ContainsRecipe() -> Bool
    {
        if recipes.count >= targetRecipe.count
        {
            return targetRecipe == recipes[recipes.count-targetRecipe.count..<recipes.count].filter({_ in true})
        }
        return false
    }
    while recipes.count < recipeIterations + lastTen
    {
        RecipeIteration()
    }
    print("14-01:", recipes[recipeIterations..<recipeIterations+lastTen])
    recipes = [3, 7]
    elves = [0 ,1]
    while !ContainsRecipe()
    {
        RecipeIteration()
    }
    print("14-02:", recipes.count-targetRecipe.count)
}

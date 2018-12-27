#!/usr/bin/env node

"use strict";

const { parseLine } = require("./part1");
const { bigGame } = require("./part2");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const game = parseLine(lines[0]);

console.log("Day 09, part 1: " + game.highscore);
//end::part1[]

// tag::part2[]
console.log("Day 09, part 2: " + bigGame(game, 100).highscore);
// end::part2[]

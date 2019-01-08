#!/usr/bin/env node

"use strict";

const { Pots } = require("./part2");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const [initialState, ...rules] = lines;
const pots = new Pots(initialState, rules);
for (let i = 0; i < 20; i++) {
  pots.nextGen();
}
console.log("Day 12, part 1: " + pots.addNumbers());
//end::part1[]

/*
// tag::part2BruteForce[]
for (let i = 20; i < 50000000000; i++) {
  if (i % 1000 === 0) console.log(`Generation: ${i}`);
  pots.nextGen();
}
console.log("Day 12, part 2: " + pots.addNumbers());
// end::part2BruteForce[]
*/

// tag::part2[]
pots.warmup();
console.log(
  "Day 12, part 2: " +
    (pots.addNumbers() + (50000000000 - pots.gen) * pots.numbersDiff)
);
// end::part2[]

#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { Pots, Pot } = require("./part2");

assert.equal(new Pot(0, "#").toString(), "..#..");
assert.equal(new Pot(0, ".").toString(), ".....");
assert.equal(new Pot(0, ".", new Pot(-1, "#")).toString(), ".#...");
assert.equal(
  new Pot(0, ".", new Pot(-1, "#", new Pot(-2, "#"))).toString(),
  "##..."
);
assert.equal(new Pot(0, ".", null, new Pot(1, "#")).toString(), "...#.");
assert.equal(
  new Pot(0, ".", null, new Pot(1, "#", null, new Pot(2, "#"))).toString(),
  "...##"
);

const testInput = [
  "initial state: #..#.#..##......###...###",
  "...## => #",
  "..#.. => #",
  ".#... => #",
  ".#.#. => #",
  ".#.## => #",
  ".##.. => #",
  ".#### => #",
  "#.#.# => #",
  "#.### => #",
  "##.#. => #",
  "##.## => #",
  "###.. => #",
  "###.# => #",
  "####. => #"
];

const [initialState, ...rules] = testInput;
const pots = new Pots(initialState, rules);

assert.equal(pots.pot(0).id, 0);
assert.equal(pots.pot(0).state, "#");
assert.equal(pots.pot(0).next.id, 1);
assert.equal(pots.pot(0).next.state, ".");
assert.equal(pots.nextGenValue(pots.pot(0)), "#");

assert.equal(pots.pot(3).state, "#");
assert.equal(pots.nextGenValue(pots.pot(3)), ".");

assert.equal(pots.pot(1).toString(), ".#..#");

pots.nextGen();
assert.equal(pots.pot(5).state, ".");

pots.nextGen();
assert.equal(pots.pot(5).state, "#");
assert.equal(pots.pot(25).state, "#");

pots.nextGen();
assert.equal(pots.pot(-1).state, "#");

pots.nextGen();
assert.equal(pots.pot(26).state, "#");

for (let i = 5; i <= 20; i++) pots.nextGen();

assert.equal(pots.pot(-2).state, "#");

assert.equal(pots.numbers, 325);

assert.equal(pots.toString(), ".#....##....#####...#######....#.#..##.");

for (let i = 21; i <= 200; i++) {
  pots.nextGen();
  console.log(pots.toString());
}

let numbers = pots.numbers;
for (let i = 201; i <= 300; i++) {
  pots.nextGen();
  const newNumbers = pots.numbers;
  console.log(
    `Iteration: ${i}, Value: ${newNumbers}, diff: ${newNumbers - numbers}`
  );
  numbers = newNumbers;
}
assert.equal(pots.gen, 300);

const newPots = new Pots(initialState, rules);
newPots.warmup();
assert.equal(newPots.numbersDiff, 20);
assert.equal(newPots.gen, 87);

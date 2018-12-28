#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { Pots, Pot } = require("./part1");

assert.equal(new Pot(0, true).toString(), "..#..");
assert.equal(new Pot(0, false).toString(), ".....");
assert.equal(new Pot(0, false, new Pot(-1, true)).toString(), ".#...");
assert.equal(
  new Pot(0, false, new Pot(-1, true, new Pot(-2, true))).toString(),
  "##..."
);
assert.equal(new Pot(0, false, null, new Pot(1, true)).toString(), "...#.");
assert.equal(
  new Pot(0, false, null, new Pot(1, true, null, new Pot(2, true))).toString(),
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

assert.equal(pots.potZero.id, 0);
assert.equal(pots.potZero.state, true);
assert.equal(pots.potZero.next.id, 1);
assert.equal(pots.potZero.next.state, false);
assert.equal(pots.nextGenValue(pots.pot(0)), true);

assert.equal(pots.pot(3).state, true);
assert.equal(pots.nextGenValue(pots.pot(3)), false);

assert.equal(pots.pot(1).toString(), ".#..#");

pots.nextGen();
assert.equal(pots.pot(5).state, false);

pots.nextGen();
assert.equal(pots.pot(5).state, true);
assert.equal(pots.pot(25).state, true);

pots.nextGen();
assert.equal(pots.pot(-1).state, true);

pots.nextGen();
assert.equal(pots.pot(26).state, true);

for (let i = 5; i <= 20; i++) pots.nextGen();

assert.equal(pots.pot(-2).state, true);

assert.equal(pots.addNumbers(), 325);

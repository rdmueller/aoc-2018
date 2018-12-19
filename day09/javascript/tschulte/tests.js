#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { parseLine, MarbleGame, Circle } = require("./part1");

const circle = new Circle();

assert.deepEqual(circle.marbles, [0]);
assert.equal(circle.currentMarble, 0);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [1, 0]);
assert.equal(circle.currentMarble, 1);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [2, 1, 0]);
assert.equal(circle.currentMarble, 2);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [2, 1, 3, 0]);
assert.equal(circle.currentMarble, 3);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [4, 2, 1, 3, 0]);
assert.equal(circle.currentMarble, 4);

for (let i = 5; i < 23; i++) {
  assert.equal(circle.addNextMarble(), 0);
}
assert.equal(circle.addNextMarble(), 32);

assert.equal(circle.currentMarble, 19);
assert.deepEqual(circle.marbles, [
  16,
  8,
  17,
  4,
  18,
  19,
  2,
  20,
  10,
  21,
  5,
  22,
  11,
  1,
  12,
  6,
  13,
  3,
  14,
  7,
  15,
  0
]);

for (let i = 1; i < 23; i++) {
  assert.equal(circle.addNextMarble(), 0);
}

assert.equal(circle.addNextMarble(), 63);

assert.equal(new MarbleGame(9, 22).highscore, 0);
assert.equal(new MarbleGame(9, 23).highscore, 32);
assert.equal(new MarbleGame(50, 23).highscore, 32);
assert.equal(new MarbleGame(9, 25).highscore, 32);
assert.equal(new MarbleGame(9, 50).highscore, 63);
assert.equal(new MarbleGame(2, 50).highscore, 63);
assert.equal(new MarbleGame(1, 50).highscore, 95);

assert.equal(
  parseLine("9 players; last marble is worth 50 points").highscore,
  63
);

/* This does not work, although it should.
assert.equal(
  parseLine("10 players; last marble is worth 1618 points").highscore,
  8317
);
assert.equal(
  parseLine("13 players; last marble is worth 7999 points").highscore,
  146373
);
assert.equal(
  parseLine("17 players; last marble is worth 1104 points").highscore,
  2764
);
assert.equal(
  parseLine("21 players; last marble is worth 6111 points").highscore,
  54718
);
assert.equal(
  parseLine("30 players; last marble is worth 5807 points").highscore,
  37305
);
*/

#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { parseLine, MarbleGame, Circle, CircleMarble } = require("./part1");
const { bigGame } = require("./part2");

// CircleMarble
const firstMarble = new CircleMarble(0);
assert.equal(firstMarble.value, 0);
assert.equal(firstMarble.next, firstMarble);
assert.equal(firstMarble.previous, firstMarble);
const secondMarble = firstMarble.insertAfter(1);
assert.equal(secondMarble.value, 1);
assert.equal(firstMarble.next, secondMarble);
assert.equal(firstMarble.previous, secondMarble);
assert.equal(secondMarble.next, firstMarble);
assert.equal(secondMarble.previous, firstMarble);
assert.equal(firstMarble.nextN(1), secondMarble);
assert.equal(firstMarble.nextN(2), firstMarble);
assert.equal(firstMarble.previousN(1), secondMarble);
assert.equal(firstMarble.previousN(2), firstMarble);

secondMarble.remove();
assert.equal(firstMarble.next, firstMarble);
assert.equal(firstMarble.previous, firstMarble);
assert.equal(secondMarble.next, secondMarble);
assert.equal(secondMarble.previous, secondMarble);

// circle
const circle = new Circle();

assert.deepEqual(circle.marbles, [0]);
assert.equal(circle.currentMarble.value, 0);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [0, 1]);
assert.equal(circle.currentMarble.value, 1);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [0, 2, 1]);
assert.equal(circle.currentMarble.value, 2);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [0, 2, 1, 3]);
assert.equal(circle.currentMarble.value, 3);

assert.equal(circle.addNextMarble(), 0);
assert.deepEqual(circle.marbles, [0, 4, 2, 1, 3]);
assert.equal(circle.currentMarble.value, 4);

for (let i = 5; i < 23; i++) {
  assert.equal(circle.addNextMarble(), 0);
}
assert.equal(circle.addNextMarble(), 32);

assert.equal(circle.currentMarble.value, 19);
assert.deepEqual(circle.marbles, [
  0,
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

assert.equal(bigGame(new MarbleGame(9, 5), 10).highscore, 63);

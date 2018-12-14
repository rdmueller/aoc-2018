#!/usr/bin/env node

"use strict";

const assert = require("assert");
const {
  parseLine,
  areaOfCoords,
  isInfinite,
  distance,
  findLargestArea
} = require("./part1");

const testinput = ["1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9"];

assert.deepStrictEqual(parseLine("1, 1"), [1, 1]);
assert.deepStrictEqual(parseLine("8, 3"), [8, 3]);

const coords = testinput.map(parseLine);

assert.deepStrictEqual(coords, [
  [1, 1],
  [1, 6],
  [8, 3],
  [3, 4],
  [5, 5],
  [8, 9]
]);

const area = areaOfCoords(coords);
assert.deepStrictEqual(area, {
  minX: 1,
  maxX: 8,
  minY: 1,
  maxY: 9
});

assert.strictEqual(isInfinite(area, coords[0]), true);
assert.strictEqual(isInfinite(area, coords[1]), true);
assert.strictEqual(isInfinite(area, coords[2]), true);
assert.strictEqual(isInfinite(area, coords[3]), false);
assert.strictEqual(isInfinite(area, coords[4]), false);
assert.strictEqual(isInfinite(area, coords[5]), true);

assert.strictEqual(distance(coords[0], coords[3]), 5);
assert.strictEqual(distance(coords[3], coords[4]), 3);

assert.strictEqual(findLargestArea(coords), 17);

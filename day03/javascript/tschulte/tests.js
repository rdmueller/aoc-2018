#!/usr/bin/env node

const assert = require("assert");
const parseLine = require("./part1.js").parseLine;
const intersection = require("./part1.js").intersection;
const intersectingInches = require("./part1.js").intersectingInches;

const rectangle = parseLine("#123 @ 3,2: 5x4");
assert.equal(rectangle.id, 123);
assert.equal(rectangle.x, 3);
assert.equal(rectangle.y, 2);
assert.equal(rectangle.width, 5);
assert.equal(rectangle.height, 4);

assert.deepEqual(parseLine("#123 @ 3,2: 5x4"), {
  id: 123,
  x: 3,
  y: 2,
  width: 5,
  height: 4
});

/*
#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2
#4 @ 2,2: 4x4
#5 @ 3,3: 1x1

Visually, these claim the following areas:
  012345678
 ---------
0|........
1|...2222.
2|..4XXX2.
3|.1XXXX2.
4|.1XXXX2.
5|.1XXXX3.
6|.111133.
7|........
*/
const line1 = "#1 @ 1,3: 4x4";
const line2 = "#2 @ 3,1: 4x4";
const line3 = "#3 @ 5,5: 2x2";
const line4 = "#4 @ 2,2: 4x4";
const line5 = "#5 @ 3,3: 1x1";
const rect1 = parseLine(line1);
const rect2 = parseLine(line2);
const rect3 = parseLine(line3);
const rect4 = parseLine(line4);
const rect5 = parseLine(line5);
assert.deepEqual(intersection(rect1, rect3), null);
assert.deepEqual(intersection(rect2, rect3), null);
assert.deepEqual(intersection(rect1, rect2), {
  x: 3,
  y: 3,
  width: 2,
  height: 2
});
assert.deepEqual(intersection(rect1, rect4), {
  x: 2,
  y: 3,
  width: 3,
  height: 3
});
assert.deepEqual(intersection(rect2, rect4), {
  x: 3,
  y: 2,
  width: 3,
  height: 3
});
assert.deepEqual(intersection(rect3, rect4), {
  x: 5,
  y: 5,
  width: 1,
  height: 1
});

assert.equal(intersectingInches([rect1, rect2, rect3, rect4, rect5]), 15);

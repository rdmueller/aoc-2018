#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { parseLine, Lights } = require("./part1");

const testInput = [
  "position=< 9,  1> velocity=< 0,  2>",
  "position=< 7,  0> velocity=<-1,  0>",
  "position=< 3, -2> velocity=<-1,  1>",
  "position=< 6, 10> velocity=<-2, -1>",
  "position=< 2, -4> velocity=< 2,  2>",
  "position=<-6, 10> velocity=< 2, -2>",
  "position=< 1,  8> velocity=< 1, -1>",
  "position=< 1,  7> velocity=< 1,  0>",
  "position=<-3, 11> velocity=< 1, -2>",
  "position=< 7,  6> velocity=<-1, -1>",
  "position=<-2,  3> velocity=< 1,  0>",
  "position=<-4,  3> velocity=< 2,  0>",
  "position=<10, -3> velocity=<-1,  1>",
  "position=< 5, 11> velocity=< 1, -2>",
  "position=< 4,  7> velocity=< 0, -1>",
  "position=< 8, -2> velocity=< 0,  1>",
  "position=<15,  0> velocity=<-2,  0>",
  "position=< 1,  6> velocity=< 1,  0>",
  "position=< 8,  9> velocity=< 0, -1>",
  "position=< 3,  3> velocity=<-1,  1>",
  "position=< 0,  5> velocity=< 0, -1>",
  "position=<-2,  2> velocity=< 2,  0>",
  "position=< 5, -2> velocity=< 1,  2>",
  "position=< 1,  4> velocity=< 2,  1>",
  "position=<-2,  7> velocity=< 2, -2>",
  "position=< 3,  6> velocity=<-1, -1>",
  "position=< 5,  0> velocity=< 1,  0>",
  "position=<-6,  0> velocity=< 2,  0>",
  "position=< 5,  9> velocity=< 1, -2>",
  "position=<14,  7> velocity=<-2,  0>",
  "position=<-3,  6> velocity=< 2, -1>"
];

const parsed = testInput.map(parseLine);

assert.equal(parsed[0].x, 9);
assert.equal(parsed[0].y, 1);
assert.equal(parsed[0].vx, 0);
assert.equal(parsed[0].vy, 2);
assert.equal(parsed[5].x, -6);
assert.equal(parsed[2].y, -2);
assert.equal(parsed[1].vx, -1);
assert.equal(parsed[3].vy, -1);

assert.equal(parsed[0].move().x, 9);
assert.equal(parsed[0].move().y, 3);
assert.equal(parsed[0].move().vx, 0);
assert.equal(parsed[0].move().vy, 2);

assert.equal(parsed[1].move().x, 6);
assert.equal(parsed[1].move().y, 0);
assert.equal(parsed[1].move().vx, -1);
assert.equal(parsed[1].move().vy, 0);

const lights = new Lights(parsed);
assert.equal(lights.move().lights[0].x, 9);
assert.equal(lights.move().lights[0].y, 3);

assert.equal(lights.width, 22);
assert.equal(lights.height, 16);

const message = lights.moveUntilMessageAppears();

assert.equal(message.width, 10);
assert.equal(message.height, 8);

assert.equal(
  message.toString(),
  `\
#...#..###
#...#...#.
#...#...#.
#####...#.
#...#...#.
#...#...#.
#...#...#.
#...#..###
`
);

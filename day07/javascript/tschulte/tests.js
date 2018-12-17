#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { parseLine, executionOrder } = require("./part1");
const { parallelExecutionTime } = require("./part2");

const lines = [
  "Step C must be finished before step A can begin.",
  "Step C must be finished before step F can begin.",
  "Step A must be finished before step B can begin.",
  "Step A must be finished before step D can begin.",
  "Step B must be finished before step E can begin.",
  "Step D must be finished before step E can begin.",
  "Step F must be finished before step E can begin."
];

/*

  -->A--->B--
 /    \      \
C      -->D----->E
 \           /
  ---->F-----

*/

let steps = lines.reduce(parseLine, []);

assert.deepStrictEqual(steps.map(step => step.id), [
  "A",
  "B",
  "C",
  "D",
  "E",
  "F"
]);
assert.deepStrictEqual(steps[0].parents, [steps[2]]);
assert.deepStrictEqual(steps[0].children, [steps[1], steps[3]]);

assert.equal(executionOrder(steps), "CABDFE");

// we need fresh steps, because they have state
steps = lines.reduce(parseLine, []);
assert.equal(parallelExecutionTime(steps, 2, 0), 15);

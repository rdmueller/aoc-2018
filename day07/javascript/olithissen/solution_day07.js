#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list.getEntries().forEach(item => console.log(item.name + ": " + item.duration));
  obs.disconnect();
});

/**
 * Reads an input file as an array by splitting it line by line to an array of tuples.
 * Each tuple of ['A', 'B'] is to be interpreted as 'A' unlocks 'B'
 */
function readInputAsArray(file) {
  return fs
    .readFileSync(file, "utf8")
    .split("\r\n")
    .map(line => {
      let items = line.split(/Step ([A-Z]) must be finished before step ([A-Z]) can begin\./);
      return [items[1], items[2]];
    });
}

const reduceTuples = (a, c) => {
  a.push(c[0], c[1]);
  return a;
};
const makeUnique = (a, c) => {
  if (a.indexOf(c) === -1) {
    a.push(c);
  }
  return a;
};
/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  let nodes = data
    .reduce(reduceTuples, [])
    .reduce(makeUnique, [])
    .map(item => {
      const prev = data.filter(t => t[1] === item).map(t => t[0]);
      const next = data.filter(t => t[0] === item).map(t => t[1]);
      return { 
        id: item, 
        locked: true,
        next: next,
        prev: prev
      };
    });

  console.log(nodes);
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");

// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == "CABDFE");

// obs.observe({ entryTypes: ["function"] });
// const timerify_part1 = performance.timerify(part1);
// const realResult01 = timerify_part1(realData01);
// assert(realResult01 == 10450, "Good job, you broke a working solution ... ");
// console.log("RESULT: " + realResult01);

// Part 2
// const testResult02 = part2(testdataPart01);
// console.log("Test result: " + testResult02);
// assert(testResult02 == 4);

// obs.observe({ entryTypes: ["function"] });
// const timerify_part2 = performance.timerify(part2);
// const realResult02 = timerify_part2(realData01);
// assert(realResult02 == 4624, "Good job, you broke a working solution ... ");
// console.log("RESULT: " + realResult02);

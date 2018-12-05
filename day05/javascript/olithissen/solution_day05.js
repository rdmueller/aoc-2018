#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list.getEntries().forEach(item => console.log(item.name + ": " + item.duration));
  obs.disconnect();
});

/**
 * Reads an input file as an array by splitting it  char by char
 */
function readInputAsArray(file) {
  return fs.readFileSync(file, "utf8").split("");
}

function react(array, pivot) {
  if (pivot >= 0 && pivot + 1 < array.length) {
    if (isPairReactable(array[pivot], array[pivot + 1])) {
      array.splice(pivot, 2);
      return true;
    }
  }
  return false;
}

function isPairReactable(char1, char2) {
  return (
    char1.toLowerCase() == char2.toLowerCase() &&
    ((isLowerCase(char1) && isUpperCase(char2)) || (isUpperCase(char1) && isLowerCase(char2)))
  );
}

function isUpperCase(char) {
  return char == char.toUpperCase();
}

function isLowerCase(char) {
  return char == char.toLowerCase();
}

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  let array = data.slice(0);

  let i = 0;
  while (i < array.length - 1) {
    // This is awesome! Changing from i = 0 to i-- is 11x faster!
    // react(array, i) ? i = 0 : i++;
    react(array, i) ? i-- : i++;
  }

  return array.length;
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  const polymers = "abcdefghijklmnopqrstuvwxyz".split("");

  const list = polymers.map(polymer => {
    let array = data.filter(i => i.toLowerCase() != polymer);
    let i = 0;
    while (i < array.length - 1) {
      react(array, i) ? i-- : i++;
    }

    return { polymer: polymer, length: array.length };
  });

  const smallestResult = list.reduce((acc, cur) => {
    return Math.min(cur.length, acc);
  }, data.length);

  return smallestResult;
}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");
// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 10);

obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(realData01);
assert(realResult01 == 10450, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult01);

// Part 2
const testResult02 = part2(testdataPart01);
console.log("Test result: " + testResult02);
assert(testResult02 == 4);

obs.observe({ entryTypes: ["function"] });
const timerify_part2 = performance.timerify(part2);
const realResult02 = timerify_part2(realData01);
assert(realResult02 == 4624, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult02);

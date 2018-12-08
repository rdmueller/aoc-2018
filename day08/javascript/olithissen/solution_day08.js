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
  return fs.readFileSync(file, "utf8").split(" ");
}

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  const node = processNode(data).node;
  const sum = flattenTree(node)
    .map(node => node.meta)
    .reduce((a, c) => {
      a.push(...c);
      return a;
    }, [])
    .reduce((a, c) => (a += parseInt(c)), 0);

  return sum;
}

function flattenTree(node) {
  if (node.children.length > 0) {
    let collector = [];
    collector.push(node);

    for (child of node.children) {
      collector.push(...flattenTree(child));
    }

    return collector;
  } else {
    return [node];
  }
}

let idSource = 0;
function processNode(data, parent = 0) {
  const numChildren = parseInt(data[0]);
  const numMeta = parseInt(data[1]);
  let length = 0;

  const node = {
    parent: parent,
    id: ++idSource,
    children: []
  };

  for (let i = 0; i < numChildren; i++) {
    const child = processNode(data.slice(length + 2), node.id);
    length += child.length;
    node.children.push(child.node);
  }

  (node.meta = data.slice(2 + length, numMeta + 2 + length)), (length += parseInt(numMeta) + 2);
  return { node, length };
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  return 0;
}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");

// Part 1
// assert(processNode(testdataPart01.slice(2)).length == 5);
// assert(processNode(testdataPart01.slice(7)).length == 6);
// assert(processNode(testdataPart01.slice(0)).length == 16);
// console.log(JSON.stringify(processNode(testdataPart01.slice(7)).node, null, 2));
// console.log(JSON.stringify(flattenTree(processNode(testdataPart01.slice(7)).node)));

const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 138);

obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(realData01);
console.log("RESULT: " + realResult01);
assert(realResult01 == 43351, "Good job, you broke a working solution ... ");

// Part 2
// const testResult02 = part2(testdataPart01);
// console.log("Test result: " + testResult02);
// assert(testResult02 == 4);

// obs.observe({ entryTypes: ["function"] });
// const timerify_part2 = performance.timerify(part2);
// const realResult02 = timerify_part2(realData01);
// console.log("RESULT: " + realResult02);
// assert(realResult02 == 1, "Good job, you broke a working solution ... ");

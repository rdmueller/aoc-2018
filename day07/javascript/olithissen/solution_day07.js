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

const flatten = (a, c) => {
  a.push(...c);
  return a;
};
/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  const mapNodeInfo = item => {
    const prev = data.filter(t => t[1] === item).map(t => t[0]);
    const next = data.filter(t => t[0] === item).map(t => t[1]);
    return {
      id: item,
      processed: false,
      locked: prev.length > 0 ? true : false,
      next: next,
      prev: prev
    };
  };

  let nodes = data
    .reduce(reduceTuples, [])
    .reduce(makeUnique, [])
    .map(mapNodeInfo);

  const workNodes = nodes.slice(0);
  let chain = [];

  // loop while not all nodes are processed
  while (!workNodes.every(item => item.processed)) {
    // get all nodes ready for processing (unprocessed and unlocked) and order them ascending
    const unlocked = workNodes
      .filter(item => !item.locked && !item.processed)
      .sort((a, b) => a.id.charCodeAt(0) - b.id.charCodeAt(0));
    const node = unlocked[0];

    chain.push(node);
    node.processed = true;

    workNodes
      .filter(item => {
        let predecessors = item.prev
          .map(id => workNodes.filter(n1 => n1.id === id))
          .reduce(flatten, []);
        let allPredecessorsProcessed = predecessors.every(pd => pd.processed);
        return allPredecessorsProcessed;
      })
      .forEach(item => (item.locked = false));
  }

  return chain.map(item => item.id).join("");
}

function taskDuration(id, delay) {
  return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(id) + 1 + delay;
}

const lanes = {
  init: function(lanes) {
    for (let i = 0; i < lanes; i++) {
      this.lanes[i] = "";
    }
  },
  lanes: [],

  current: 0,
  progress: function() {
    this.current++;
  },

  hasFreeLane: function() {
    return this.nextFreeLane() !== 0;
  },

  numberOfFreeLanes: function() {
    return this.lanes.filter(l => !lane.charAt(this.current)).length;
  },

  nextFreeLane: function() {
    for (let lane in this.lanes) {
      if (!this.lanes[lane].charAt(this.current)) {
        return lane;
      }
    }
    return -1;
  },

  enqueue: function(task) {
    let lane = this.nextFreeLane();
    this.lanes[lane] += task;
  }
};

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data, delay, workers) {
  lanes.init(workers);

  const mapNodeInfo = item => {
    const prev = data.filter(t => t[1] === item).map(t => t[0]);
    const next = data.filter(t => t[0] === item).map(t => t[1]);
    return {
      id: item,
      processed: false,
      locked: prev.length > 0 ? true : false,
      next: next,
      prev: prev
    };
  };

  let nodes = data
    .reduce(reduceTuples, [])
    .reduce(makeUnique, [])
    .map(mapNodeInfo);

  const workNodes = nodes.slice(0);
  let chain = [];

  let level = 0;
  while (!workNodes.every(item => item.processed)) {
    const unlocked = workNodes
      .filter(item => !item.locked && !item.processed)
      .sort((a, b) => a.id.charCodeAt(0) - b.id.charCodeAt(0));

    unlocked.forEach(node => {
      node.processed = true;
      node.level = level;
    });
    level++;
    chain.push(...unlocked);

    workNodes
      .filter(item => {
        let predecessors = item.prev
          .map(id => workNodes.filter(n1 => n1.id === id))
          .reduce(flatten, []);
        let allPredecessorsProcessed = predecessors.every(pd => pd.processed);
        return allPredecessorsProcessed;
      })
      .forEach(item => (item.locked = false));
  }

  return chain.map(item => item.id).join("");
}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");

// Part 1
// const testResult01 = part1(testdataPart01);
// console.log("Test result: " + testResult01);
// assert(testResult01 == "CABDFE");

// obs.observe({
//   entryTypes: ["function"]
// });
// const timerify_part1 = performance.timerify(part1);
// const realResult01 = timerify_part1(realData01);
// console.log("RESULT: " + realResult01);
// assert(realResult01 == "CQSWKZFJONPBEUMXADLYIGVRHT", "Good job, you broke a working solution ... ");

// Part 2
const testResult02 = part2(testdataPart01, 0, 2);
console.log("Test result: " + testResult02);
assert(testResult02 == "CAFBDE");

// obs.observe({ entryTypes: ["function"] });
// const timerify_part2 = performance.timerify(part2);
// const realResult02 = timerify_part2(realData01);
// assert(realResult02 == 4624, "Good job, you broke a working solution ... ");
// console.log("RESULT: " + realResult02);

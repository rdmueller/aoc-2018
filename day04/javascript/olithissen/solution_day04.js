#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list
    .getEntries()
    .forEach(item => console.log(item.name + ": " + item.duration));
  obs.disconnect();
});

/**
 * Reads an input file as an array by splitting it line by line
 */
function readInputAsArray(file) {
  return fs.readFileSync(file, "utf8").split("\r\n");
}

/**
 * A mapper to convert the input format to objects
 *
 * @param  {} line The input line in the "#1 @ 1,3: 4x4" format
 * @returns An object representing the input
 */
const mapGuardDuty = line => {
  let result = line
    // [1518-11-01 00:00] Guard #10 begins shift
    // [1518-11-01 00:05] falls asleep
    // [1518-11-01 00:25] wakes up
    .split(/\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (.*)/)
    .filter(item => item.length > 0)
    .map(item => {
      const parsed = parseInt(item, 10);
      const result = isNaN(parsed) ? item : parsed;
      return result;
    });

  const tryExtractId = result[5].match(/.*?#(\d+).*/);
  let id = null;
  let state;
  if (tryExtractId) {
    id = parseInt(tryExtractId[1]);
    state = "start shift";
  } else {
    state = result[5];
  }

  return {
    id: id,
    // I'm going to regeret this. Damn elves might have 66 minutes per hour ...
    time: new Date(
      Date.UTC(result[0], result[1] - 1, result[2], result[3], result[4], 0)
    ),
    minute: result[4],
    state: state
  };
};

const enrichId = item => {
  if (item.id) {
    currentId = item.id;
  }
  if (currentId && !item.id) {
    item.id = currentId;
  }
  return item;
};

const reduceGuardSleepCycles = (acc, cur) => {
  if (!acc[cur.id]) {
    acc[cur.id] = {
      id: cur.id,
      cycles: [],
      sleepDuration: 0,
      sleepingMinutes: {}
    };
  }
  acc[cur.id].cycles.push({
    state: cur.state,
    time: cur.time,
    minute: cur.minute
  });

  if (cur.state == "wakes up") {
    const lastOnStack = acc[cur.id].cycles.slice(-1)[0];
    const lastMinus1OnStack = acc[cur.id].cycles.slice(-2)[0];
    acc[cur.id].sleepDuration +=
      (lastOnStack.time - lastMinus1OnStack.time) / 60000;
    for (let i = lastMinus1OnStack.minute; i < lastOnStack.minute; i++) {
      if (!acc[cur.id].sleepingMinutes[i]) {
        acc[cur.id].sleepingMinutes[i] = 0;
      }
      acc[cur.id].sleepingMinutes[i]++;
    }
  }

  return acc;
};

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  // Convert input data to guard objects
  const guardDuties = data
    .map(mapGuardDuty)
    .sort((a, b) => a.time - b.time)
    .map(enrichId);

  const guardSleepCycles = guardDuties.reduce(reduceGuardSleepCycles, {});

  // OMFG, I've lost all hope
  const sleepiestGuard = parseInt(
    Object.values(guardSleepCycles).sort(
      (a, b) => b.sleepDuration - a.sleepDuration
    )[0].id
  );

  const sleepiestGuardSleepingMinutes = Object.values(guardSleepCycles).filter(
    guard => guard.id == sleepiestGuard
  )[0].sleepingMinutes;

  // Oh my god, I am so sorry!
  const sleepiestMinutePerGuard = parseInt(
    Object.keys(sleepiestGuardSleepingMinutes).reduce((a, b) =>
      sleepiestGuardSleepingMinutes[a] > sleepiestGuardSleepingMinutes[b]
        ? a
        : b
    )
  );

  return sleepiestGuard * sleepiestMinutePerGuard;
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  // Convert input data to guard objects
  const guardDuties = data
    .map(mapGuardDuty)
    .sort((a, b) => a.time - b.time)
    .map(enrichId);

  const guardSleepCycles = Object.values(
    guardDuties.reduce(reduceGuardSleepCycles, {})
  );

  const result = guardSleepCycles.map(item => {
    const maxMinute = Object.keys(item.sleepingMinutes).reduce((a, b) =>
      item.sleepingMinutes[a] > item.sleepingMinutes[b] ? a : b, 0
    );
    return { id: item.id, maxMinute: parseInt(maxMinute), count: item.sleepingMinutes[maxMinute] };
  }).sort((a, b) => b.count - a.count);

  return result[0].id * result[0].maxMinute;
}

const testdataPart01 = readInputAsArray("testdata.txt");

// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 240);
obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
console.log("RESULT: " + timerify_part1(readInputAsArray("input.txt")));

// Part 2
const testResult02 = part2(testdataPart01);
console.log("Test result: " + testResult02);
assert(testResult02 == 4455);
obs.observe({ entryTypes: ["function"] });
const timerify_part2 = performance.timerify(part2);
console.log("RESULT: " + timerify_part2(readInputAsArray("input.txt")));

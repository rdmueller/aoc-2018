#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list.getEntries().forEach(item => console.log(item.name + ": " + item.duration));
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
 * @param  {} line The input line
 * @returns An object representing the input
 */
const mapGuardDuty = line => {
  let result = line
    // [1518-11-01 00:00] Guard #10 begins shift
    // [1518-11-01 00:05] falls asleep
    // [1518-11-01 00:25] wakes up
    .split(/\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\] (.*)/) // Split by regex
    .filter(i => i.length > 0) // Take only items that are not empty
    .map(i => {
      const parsed = parseInt(i, 10); // Try to parse the item
      return isNaN(parsed) ? i : parsed; // If it's NaN return the priginal string, else the parsed number
    });

  // Try to match result[5] (the text) against a regex to see if it contains the guard-ID
  const tryExtractId = result[5].match(/.*?#(\d+).*/);
  let id = null;
  if (tryExtractId) {
    id = parseInt(tryExtractId[1]);
  }

  return {
    id: id, // guard-ID if known, might be null at first
    // I'm going to regeret this. Damn elves might have 66 minutes per hour ...
    time: new Date(Date.UTC(result[0], result[1] - 1, result[2], result[3], result[4], 0)),
    minute: result[4], // The minute part of the date. Added for convenience
    state: result[5] // "falls asleep", "wakes up", "Guard #10 begins shift"
  };
};

/**
 * Mapper for enriching the guard object with an ID. Requires a sorted list of guard duties!
 *
 * @param  {} item Guard object to work on
 */
const enrichId = item => {
  // If the current item has an id, store it to the implicitly declared global variable 'currentId'
  if (item.id) {
    currentId = item.id;
  }

  // If there is a 'currentId' and the item doesn't have an ID yet, add it.
  if (currentId && !item.id) {
    item.id = currentId;
  }

  return item;
};

/**
 * Reducer for aggregating all information for a specific guard
 *
 * @param  {} acc Accumulator
 * @param  {} cur Current item
 */
const reduceGuardSleepCycles = (acc, cur) => {
  // Create a new item for the current id in the accumulator if it doesn't exit yet
  if (!acc[cur.id]) {
    acc[cur.id] = {
      id: cur.id,
      cycles: [],
      sleepDuration: 0,
      sleepingMinutes: {}
    };
  }

  // Push the current item to the cycles array of this guard
  acc[cur.id].cycles.push(cur);

  // "wakes up" means that a "sleep cycle" is completed
  if (cur.state == "wakes up") {
    // Take the last item from the cycle stack ...
    const wokeUp = acc[cur.id].cycles.slice(-1)[0];
    // ... and the (last -1) item
    const fellAsleep = acc[cur.id].cycles.slice(-2)[0];

    // Add the sleep time to the duration
    acc[cur.id].sleepDuration += wokeUp.minute - fellAsleep.minute;

    // Loop over all sleeping minutes
    for (let i = fellAsleep.minute; i < wokeUp.minute; i++) {
      // Initialize and/or increase the counter for the "sleeping minute" if it doesn't exist yet
      if (!acc[cur.id].sleepingMinutes[i]) {
        acc[cur.id].sleepingMinutes[i] = 0;
      }
      acc[cur.id].sleepingMinutes[i]++;
    }
  }

  return acc;
};

/**
 * Maps a guards sleep cycle to an object with id, sleepiest minute and "sleepiest minute counter"
 *
 * @param  {} item
 */
const mapToSleepiestMinutesPerGuard = item => {
  const maxMinute = Object.keys(item.sleepingMinutes).reduce(
    (a, b) => (item.sleepingMinutes[a] > item.sleepingMinutes[b] ? a : b),
    0
  );
  return {
    id: item.id,
    maxMinute: parseInt(maxMinute),
    count: item.sleepingMinutes[maxMinute]
  };
};

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  // Convert input data to guard objects, sort ascending by time and enrich the missing IDs
  const guardDuties = data
    .map(mapGuardDuty)
    .sort((a, b) => a.time - b.time)
    .map(enrichId);

  // Get the sleep cycle information for each guard
  const guardSleepCycles = guardDuties.reduce(reduceGuardSleepCycles, {});

  // Get the "sleepiest guard" by comparing the sleepDuration. Keep the winner of "previous vs. current"
  const sleepiestGuard = Object.keys(guardSleepCycles).reduce((acc, cur) =>
    guardSleepCycles[cur].sleepDuration > guardSleepCycles[acc].sleepDuration ? cur : acc
  );

  // Get the sleeping minutes of the sleepiest guard
  const sleepiestGuardSleepingMinutes = guardSleepCycles[sleepiestGuard].sleepingMinutes;

  // Get the sleepiest minute of the sleepiest guard. Keep the winner of "previous vs. current"
  const sleepiestMinutePerGuard = Object.keys(sleepiestGuardSleepingMinutes).reduce((a, b) =>
    sleepiestGuardSleepingMinutes[a] > sleepiestGuardSleepingMinutes[b] ? a : b
  );

  return sleepiestGuard * sleepiestMinutePerGuard;
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  // Convert input data to guard objects, sort ascending by time and enrich the missing IDs
  const guardDuties = data
    .map(mapGuardDuty)
    .sort((a, b) => a.time - b.time)
    .map(enrichId);

  // Get the sleep cycle information for each guard. Values only this time
  const guardSleepCycles = Object.values(guardDuties.reduce(reduceGuardSleepCycles, {}));

  // Map sleep cycles and sort by "sleepiest minute count" descending
  const result = guardSleepCycles
    .map(mapToSleepiestMinutesPerGuard)
    .sort((a, b) => b.count - a.count);

  // Topmost id * topmost maxMinute
  return result[0].id * result[0].maxMinute;
}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");

// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 240);

obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(readInputAsArray("input.txt"));
assert(realResult01 == 119835, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult01);

// Part 2
const testResult02 = part2(testdataPart01);
console.log("Test result: " + testResult02);
assert(testResult02 == 4455);

obs.observe({ entryTypes: ["function"] });
const timerify_part2 = performance.timerify(part2);
const realResult02 = timerify_part2(readInputAsArray("input.txt"));
assert(realResult02 == 12725, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult02);

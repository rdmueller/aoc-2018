#!/usr/bin/env node

const assert = require("assert");
const splitLine = require("./part1.js").splitLine;
const guardBeginsShift = require("./part1.js").guardBeginsShift;
const parseLine = require("./part1.js").parseLine;
const guardMostAsleep = require("./part1.js").guardMostAsleep;
const overlappingSleepTimes = require("./part1.js").overlappingSleepTimes;

const testinput = [
  "[1518-11-01 00:00] Guard #10 begins shift",
  "[1518-11-01 00:05] falls asleep",
  "[1518-11-01 00:25] wakes up",
  "[1518-11-01 00:30] falls asleep",
  "[1518-11-01 00:55] wakes up",
  "[1518-11-01 23:58] Guard #99 begins shift",
  "[1518-11-02 00:40] falls asleep",
  "[1518-11-02 00:50] wakes up",
  "[1518-11-03 00:05] Guard #10 begins shift",
  "[1518-11-03 00:24] falls asleep",
  "[1518-11-03 00:29] wakes up",
  "[1518-11-04 00:02] Guard #99 begins shift",
  "[1518-11-04 00:36] falls asleep",
  "[1518-11-04 00:46] wakes up",
  "[1518-11-05 00:03] Guard #99 begins shift",
  "[1518-11-05 00:45] falls asleep",
  "[1518-11-05 00:55] wakes up"
];

assert.deepEqual(splitLine(testinput[0]), [0, "Guard #10 begins shift"]);
assert.equal(guardBeginsShift("Guard #10 begins shift"), 10);
assert(!guardBeginsShift("falls asleep"));

let reduced = testinput.reduce(parseLine, []);
assert.equal(reduced.length, 2);
assert.equal(reduced[1].guard, 10);
assert(!reduced[1].asleep[0]);
assert.equal(reduced[1].asleep[5], 1);
assert.equal(reduced[1].asleep[24], 2);
assert(!reduced[1].asleep[29]);
assert.equal(reduced[1].asleep[30], 1);
assert.equal(reduced[1].timeAsleep, 50);

const foundGuard = guardMostAsleep(reduced);
assert.equal(foundGuard.guard, 10);
assert.deepEqual(overlappingSleepTimes(foundGuard), [{ minute: 24, days: 2 }]);

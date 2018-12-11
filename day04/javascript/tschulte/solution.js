#!/usr/bin/env node

const parseLine = require("./part1.js").parseLine;
const guardMostAsleep = require("./part1.js").guardMostAsleep;
const overlappingSleepTimes = require("./part1.js").overlappingSleepTimes;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");

// tag::part1[]
const lines = input
  .split("\n")
  .filter(line => line)
  .sort();

const reduced = lines.reduce(parseLine, []);
const foundGuard = guardMostAsleep(reduced);
const id = foundGuard.guard;
const overlappingTimes = overlappingSleepTimes(foundGuard);

console.log("Day 04, part 1: " + id * overlappingTimes[0].minute);
//end::part1[]

// tag::part2[]
const part2Guards = reduced
  .map(guard => {
    return { guard: guard, times: overlappingSleepTimes(guard) };
  })
  .filter(guard => guard.times.length > 0);
part2Guards.sort((a, b) => b.times[0].days - a.times[0].days);
const part2Guard = part2Guards[0];
console.log(
  "Day 04, part 2: " + part2Guard.guard.guard * part2Guard.times[0].minute
);
// end::part2[]

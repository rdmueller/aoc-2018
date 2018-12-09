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

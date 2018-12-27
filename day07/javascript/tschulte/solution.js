#!/usr/bin/env node

"use strict";

const { parseLine, executionOrder } = require("./part1");
const { parallelExecutionTime } = require("./part2");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
let steps = lines.reduce(parseLine, []);
console.log("Day 07, part 1: " + executionOrder(steps));
//end::part1[]

// we need fresh steps, because they have state
steps = lines.reduce(parseLine, []);
// tag::part2[]
const duration = parallelExecutionTime(steps, 5, 60);
console.log("Day 07, part 2: " + duration);
// end::part2[]

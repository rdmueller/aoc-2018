#!/usr/bin/env node

"use strict";

const { parseLine, findLargestArea } = require("./part1");
const { findAreaWithin } = require("./part2");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const coords = lines.map(parseLine);
const largestArea = findLargestArea(coords);
console.log("Day 06, part 1: " + largestArea);
//end::part1[]

// tag::part2[]
const areaWithin = findAreaWithin(coords, 10000);
console.log("Day 06, part 2: " + areaWithin);
// end::part2[]

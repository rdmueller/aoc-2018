#!/usr/bin/env node

"use strict";

const { parseLine } = require("./parts");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const rootNode = parseLine(lines[0]);
console.log("Day 05, part 1: " + rootNode.sumOfMetadataDeep());
//end::part1[]

// tag::part2[]
console.log("Day 05, part 2: " + rootNode.value());
// end::part2[]

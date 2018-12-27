#!/usr/bin/env node

"use strict";

const { parseLine } = require("./part1");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]

console.log("Day 12, part 1: ");
//end::part1[]

// tag::part2[]
console.log("Day 12, part 2: ");
// end::part2[]

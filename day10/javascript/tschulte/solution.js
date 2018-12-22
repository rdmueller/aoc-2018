#!/usr/bin/env node

"use strict";

const { parseLine, Lights } = require("./part1");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const message = new Lights(lines.map(parseLine)).moveUntilMessageAppears();
console.log("Day 05, part 1: ");
console.log(message.toString());
//end::part1[]

// tag::part2[]
console.log("Day 05, part 2: " + message.time + "s");
// end::part2[]

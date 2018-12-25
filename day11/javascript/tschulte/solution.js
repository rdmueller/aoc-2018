#!/usr/bin/env node

"use strict";

const { PowerGrid } = require("./part1");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const powerGrid = new PowerGrid(Number(lines[0]));
const highestSubGrid = powerGrid.findHighestSubGrid();
const { x, y } = highestSubGrid.upperLeft;
console.log(`Day 05, part 1: ${x},${y}`);
//end::part1[]

// tag::part2[]
console.log("Day 05, part 2: ");
// end::part2[]

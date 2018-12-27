#!/usr/bin/env node

"use strict";

const { PowerGrid } = require("./part1");
const { findHighestSubGrid } = require("./part2");

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

// tag::part1[]
const powerGrid = new PowerGrid(Number(lines[0]));
const highestSubGrid = powerGrid.findHighestSubGrid();
console.log(
  `Day 11, part 1: ${highestSubGrid.upperLeft.x},${highestSubGrid.upperLeft.y}`
);
//end::part1[]

// tag::part2[]
const highestTotalSubGrid = findHighestSubGrid(powerGrid);
console.log(
  `Day 11, part 2: ${highestTotalSubGrid.upperLeft.x},${
    highestTotalSubGrid.upperLeft.y
  },${highestTotalSubGrid.size}`
);
// end::part2[]

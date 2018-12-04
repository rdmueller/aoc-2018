#!/usr/bin/env node

const checksum = require("./part1.js").checksum;
const correctBoxId = require("./part2.js").correctBoxId;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

console.log("Day 02, part 1: " + checksum(lines));

console.log("Day 02, part 2: " + correctBoxId(lines));

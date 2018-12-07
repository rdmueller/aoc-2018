#!/usr/bin/env node

const parseLine = require("./part1.js").parseLine;
const intersectingInches = require("./part1.js").intersectingInches;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

const rects = lines.map(parseLine);

console.log("Day 03, part 1: " + intersectingInches(rects));

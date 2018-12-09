#!/usr/bin/env node

const parseLine = require("./part1.js").parseLine;
const intersections = require("./part1.js").intersections;
const intersectingInches = require("./part1.js").intersectingInches;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

const rects = lines.map(parseLine);
const intersectionArray = intersections(rects);

console.log("Day 03, part 1: " + intersectingInches(intersectionArray));

// tag::part2[]
const intersectingIds = [].concat.apply([], intersectionArray.map(i => i.ids));
const allIds = rects.map(rect => rect.id);
const nonIntersectingIds = allIds.filter(id => !intersectingIds.includes(id));

console.log("Day 03, part 2: " + nonIntersectingIds);
// end::part2[]

#!/usr/bin/env node

var firstDuplicateFrequency = require("./part2.js").firstDuplicateFrequency;

// tag::readFile[]
const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
// end::readFile[]

// tag::splitLines[]
const lines = input.split("\n");
// end::splitLines[]

// tag::mapToNumber[]
let numbers = lines.filter(line => line).map(line => Number(line));
// end::mapToNumber[]

// tag::reduce[]
let finalFrequency = numbers.reduce((prev, cur) => prev + cur, 0);
// end::reduce[]

console.log("Day 01, part 1: " + finalFrequency);

console.log("Day 01, part 2: " + firstDuplicateFrequency(numbers));

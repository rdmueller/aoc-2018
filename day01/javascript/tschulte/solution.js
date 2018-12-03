#!/usr/bin/env node

// tag::readFile[]
const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
// end::readFile[]

// tag::splitLines[]
const lines = input.split("\n");
// end::splitLines[]

// tag::mapToNumber[]
let numbers = lines.map(line => Number(line));
// end::mapToNumber[]

// tag::reduce[]
let solution = numbers.reduce((prev, cur) => prev + cur, 0);
// end::reduce[]

console.log("Day 01, part 1: " + solution);

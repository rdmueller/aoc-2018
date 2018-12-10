#!/usr/bin/env node

"use strict";

const reduce = require("./part1.js").reduce;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");

// tag::part1[]
const lines = input.split("\n").filter(line => line);
const reduced = reduce(lines[0]);

console.log("Day 05, part 1: " + reduced.length);
//end::part1[]

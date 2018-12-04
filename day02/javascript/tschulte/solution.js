#!/usr/bin/env node

var checksum = require("./part1.js").checksum;

const fs = require("fs");
const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split("\n").filter(line => line);

console.log("Day 02, part 1: " + checksum(lines));

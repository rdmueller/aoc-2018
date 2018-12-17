#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { parseLine } = require("./part1");

assert.deepEqual(parseLine("0 1 99").children, []);
assert.deepEqual(parseLine("0 1 99").metadata, [99]);

assert.equal(parseLine("1 1 0 1 99 2").children.length, 1);
assert.deepEqual(parseLine("1 1 0 1 99 2").children[0].children, []);
assert.deepEqual(parseLine("1 1 0 1 99 2").children[0].metadata, [99]);
assert.deepEqual(parseLine("1 1 0 1 99 2").metadata, [2]);

assert.equal(
  parseLine("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2").sumOfMetadataDeep(),
  138
);

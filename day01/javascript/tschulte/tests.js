#!/usr/bin/env node

const assert = require("assert");
var firstDuplicateFrequency = require("./part2.js").firstDuplicateFrequency;

assert.equal(firstDuplicateFrequency([1, -2, 3, 1]), 2);

assert.equal(firstDuplicateFrequency([1, -1]), 0);
assert.equal(firstDuplicateFrequency([3, 3, 4, -2, -4]), 10);
assert.equal(firstDuplicateFrequency([-6, 3, 8, 5, -6]), 5);
assert.equal(firstDuplicateFrequency([7, 7, -2, -7, -4]), 14);

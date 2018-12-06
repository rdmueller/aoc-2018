#!/usr/bin/env node

const assert = require("assert");
const histogram = require("./part1.js").histogram;
const nTimes = require("./part1.js").nTimes;
const checksum = require("./part1.js").checksum;
const commonString = require("./part2.js").commonString;
const correctBoxId = require("./part2.js").correctBoxId;

// tag::histogram[]
assert.deepEqual(histogram(""), {});
assert.deepEqual(histogram("a"), { a: 1 });
assert.deepEqual(histogram("abcdef"), {
  a: 1,
  b: 1,
  c: 1,
  d: 1,
  e: 1,
  f: 1
});
// end::histogram[]

// tag::nTimes[]
assert.equal(nTimes(2, {}), 0);
assert.equal(nTimes(2, { a: 1 }), 0);
assert.equal(nTimes(2, { a: 2 }), 1);
assert.equal(nTimes(2, { a: 2, b: 2 }), 1);
assert.equal(nTimes(2, { a: 1, b: 2 }), 1);
assert.equal(nTimes(2, { a: 3 }), 0);
// end::nTimes[]

// tag::checksum[]
assert.equal(checksum([]), 0);
assert.equal(checksum(["abcde"]), 0);
assert.equal(checksum(["aabcde"]), 0);
assert.equal(checksum(["aabbbcde"]), 1);
assert.equal(checksum(["aabcde", "aaabcde"]), 1);
// end::checksum[]

// part 2:
assert.equal(commonString("a", "b"), "");
assert.equal(commonString("aa", "ab"), "a");
assert.equal(commonString("abc", "axc"), "ac");
assert.equal(commonString("abc", "axca"), "ac");
assert.equal(commonString("abca", "axc"), "ac");

const testInput = [
  "abcde",
  "fghij",
  "klmno",
  "pqrst",
  "fguij",
  "axcye",
  "wvxyz"
];

const expectedResult = "fgij";

assert.equal(correctBoxId(testInput), "fgij");

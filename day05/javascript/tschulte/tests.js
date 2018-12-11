#!/usr/bin/env node

"use strict";

const assert = require("assert");
const reduce = require("./part1.js").reduce;
const reduceWithoutUnit = require("./part2.js").reduceWithoutUnit;
const findShortestReduction = require("./part2.js").findShortestReduction;

assert.equal(reduce(""), "");
assert.equal(reduce("a"), "a");
assert.equal(reduce("aa"), "aa");
assert.equal(reduce("aA"), "");
assert.equal(reduce("aAa"), "a");
assert.equal(reduce("aAaA"), "");
assert.equal(reduce("dabAcCaCBAcCcaDA"), "dabCBAcaDA");

assert.equal(reduceWithoutUnit("", "a"), "");
assert.equal(reduceWithoutUnit("a", "a"), "");
assert.equal(reduceWithoutUnit("aa", "a"), "");
assert.equal(reduceWithoutUnit("aA", "a"), "");
assert.equal(reduceWithoutUnit("aAa", "a"), "");
assert.equal(reduceWithoutUnit("aAaA", "a"), "");
assert.equal(reduceWithoutUnit("cC", "a"), "");
assert.equal(reduceWithoutUnit("aAcaAC", "a"), "");
assert.equal(reduceWithoutUnit("dabAcCaCBAcCcaDA", "a"), "dbCBcD");
assert.equal(reduceWithoutUnit("dabAcCaCBAcCcaDA", "b"), "daCAcaDA");
assert.equal(reduceWithoutUnit("dabAcCaCBAcCcaDA", "c"), "daDA");
assert.equal(reduceWithoutUnit("dabAcCaCBAcCcaDA", "d"), "abCBAc");

assert.equal(findShortestReduction("dabAcCaCBAcCcaDA"), "daDA");

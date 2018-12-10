#!/usr/bin/env node

"use strict";

const assert = require("assert");
const reduce = require("./part1.js").reduce;
const reduceWithoutUnit = require("./part1.js").reduceWithoutUnit;

assert.equal(reduce(""), "");
assert.equal(reduce("a"), "a");
assert.equal(reduce("aa"), "aa");
assert.equal(reduce("aA"), "");
assert.equal(reduce("aAa"), "a");
assert.equal(reduce("aAaA"), "");
assert.equal(reduce("dabAcCaCBAcCcaDA"), "dabCBAcaDA");

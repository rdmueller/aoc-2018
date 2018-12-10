"use strict";

const reduce = require("./part1.js").reduce;

function reduceWithoutUnit(polymers, unit) {
  return reduce(polymers.replace(new RegExp("" + unit + "+", "gi"), ""));
}

function findShortestReduction(polymers) {
  const chars = "abcdefghijklmnopqrstuvwxyz";
  const length = chars.length;
  let shortestReduction = reduce(polymers);
  for (let i = 0; i < length; i++) {
    const reduced = reduceWithoutUnit(polymers, chars.charAt(i));
    if (reduced.length < shortestReduction.length) {
      shortestReduction = reduced;
    }
  }
  return shortestReduction;
}

exports.reduceWithoutUnit = reduceWithoutUnit;
exports.findShortestReduction = findShortestReduction;

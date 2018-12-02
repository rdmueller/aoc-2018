#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");

function readInputAsArray() {
  return fs.readFileSync("input.txt", "utf8").split("\r\n");
}

// Solution part 1
function part1(data) {
  let result = data
    // Map over all items
    // Split each item into characters ... i.e. "abcb" -> ['a', 'b', 'c', 'b']
    .map(charSplitter()) // i.e. { 'a' : 1, 'b' : 2, 'c' : 1 }
    .map(perLineCountMapper()) // i.e. [{'2': 0, '3': 1}, {'2': 1, '3': 1}]
    .reduce(overallCountReducer(), { "2": 0, "3": 0 }); // i.e. {'2': 5, '3': 12}

  return result["2"] * result["3"];

  function charSplitter() {
    return s =>
      s
        .split("")
        // ... and reduce them to a per-line histogram
        .reduce(histogramReducer(), {});
  }

  function overallCountReducer() {
    return (acc, cur) => {
      for (let property in cur) {
        acc[property] += cur[property];
      }
      return acc;
    };
  }

  function perLineCountMapper() {
    return item => {
      let perLineCount = { "2": 0, "3": 0 };
      for (let property in item) {
        if (item[property] == 2) {
          perLineCount["2"] = 1;
        }
        if (item[property] == 3) {
          perLineCount["3"] = 1;
        }
      }
      return perLineCount;
    };
  }

  function histogramReducer() {
    return (acc, cur) => {
      // this seems ugly and/or verbose ...
      acc.hasOwnProperty(cur) ? acc[cur]++ : (acc[cur] = 1);
      return acc;
    };
  }
}

// Solution part 2
function part2(data) {
  for (let i = 0; i < data.length; i++) {
    for (let j = 0; j < data.length; j++) {
      if (distance(data[i], data[j]) == 1) {
        return intersect(data[i], data[j]).join("");
      }
    }
  }

  function distance(a, b) {
    let dist = 0;
    for (let i = 0; i < a.length; i++) {
      if (a[i] != b[i]) {
        dist++;
      }
    }
    return dist;
  }

  function intersect(a, b) {
    let intersect = [];
    for (let i = 0; i < a.length; i++) {
      if (a[i] == b[i]) {
        intersect.push(a[i]);
      }
    }
    return intersect;
  }
}

let testdata_part01 = [
  "abcdef",
  "bababc",
  "abbcde",
  "abcccd",
  "aabcdd",
  "abcdee",
  "ababab"
];

let testdata_part02 = [
  "abcde",
  "fghij",
  "klmno",
  "pqrst",
  "fguij",
  "axcye",
  "wvxyz"
];

assert(part1(testdata_part01) == 12);
console.log(part1(readInputAsArray()));

assert(part2(testdata_part02) == "fgij");
console.log(part2(readInputAsArray()));

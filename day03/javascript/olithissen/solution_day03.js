#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list
    .getEntries()
    .forEach(item => console.log(item.name + ": " + item.duration));
  obs.disconnect();
});

/**
 * Reads the default input.txt file as an array by splitting it line by line
 */
function readInputAsArray() {
  return fs.readFileSync("input.txt", "utf8").split("\r\n");
}
/**
 * Checks if two rectangles intersect
 *
 * @param  {} rectA first rectangle
 * @param  {} rectB secondt rectangle
 * @returns true if intersecting, else false
 */
function intersectionDetection(rectA, rectB) {
  // :)
  return !(
    rectB.left >= rectA.right ||
    rectB.right <= rectA.left ||
    rectB.top >= rectA.bottom ||
    rectB.bottom <= rectA.top
  );
}
/**
 * Creates a 1x1 rectangle by its left and top position
 *
 * @param  {} left left position
 * @param  {} top top position
 */
function createFromCoordinate(left, top) {
  return {
    left: left,
    top: top,
    width: 1,
    height: 1,
    right: left + 1,
    bottom: top + 1
  };
}
/**
 * A reducer to determine the maximum width and height
 * according to the processed items
 *
 * @param  {} acc The accumulator item
 * @param  {} cur The current item
 * @returns The modified accumulator
 */
const extentReducer = (acc, cur) => {
  acc.width = Math.max(cur.right, acc.width);
  acc.height = Math.max(cur.bottom, acc.height);
  return acc;
};

/**
 * A mapper to convert the input format to objects
 * e.g. "#1 @ 1,3: 4x4" => {id: 1, left: 1, right: 3, width: 4, height: 4, right: 5, bottom: 7}
 *
 * @param  {} line The input line in the "#1 @ 1,3: 4x4" format
 * @returns An object representing
 */
const mapClaims = line => {
  let result = line
    .split(/#(\d+) \@ (\d+),(\d+): (\d+)x(\d+)/)
    .filter(item => item.length > 0)
    .map(item => parseInt(item));

  return {
    id: result[0],
    left: result[1],
    top: result[2],
    width: result[3],
    height: result[4],
    right: result[1] + result[3],
    bottom: result[2] + result[4]
  };
};

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  // Convert input data to claim objects
  const claims = data.map(mapClaims);

  // Determine the extents of the "sheet" according to the claims
  const extents = claims.reduce(extentReducer, {
    width: 0,
    height: 0
  });

  // Initialize intersection counter
  let intersections = 0;

  // Iterate over each possible "inch" horizontally
  for (let i = 0; i < extents.width + 1; i++) {
    // Iterate over each possible "inch" vertically
    for (let j = 0; j < extents.height + 1; j++) {
      // Create a virtual 1x1 claim for checking intersections
      const check = createFromCoordinate(i, j);

      // filter all claims that intersect with the "virtual claim"
      const hits = claims.filter(claim => {
        return intersectionDetection(claim, check);
      });

      // If a virtual claim has 2 or more hits, increase the intersection counter
      if (hits.length >= 2) {
        intersections++;
      }
    }
  }

  return intersections;
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  // Convert input data to claim objects
  const claims = data.map(mapClaims);

  // Initialize accumulator object
  const accumulator = {
    // I'm so sick of that ...
    increase: function(id) {
      if (!this[id]) {
        this[id] = 1;
      } else {
        this[id]++;
      }
    }
  };

  // Iterate over all claims
  for (let i = 0; i < claims.length; i++) {
    // "Cross join" all claims skipping already checked claims and indentities
    // (I really like that!)
    for (let j = i + 1; j < claims.length; j++) {
      const claimA = claims[i];
      const claimB = claims[j];
      // Check if the two claims intersect
      if (intersectionDetection(claimA, claimB)) {
        // If so, increase the intersect counter for that id.
        accumulator.increase(claimA.id);
        accumulator.increase(claimB.id);
      }
    }
  }

  // The fun part: Filter all claims whose ids don't exist in the accumulator
  const result = claims.filter(claim => {
    return !accumulator.hasOwnProperty(claim.id);
  });

  return result[0].id;
}

const testdataPart01 = ["#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2"];

// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 4);
obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
// console.log("RESULT:" + timerify_part1(readInputAsArray()));

// Part 2
const testResult02 = part2(testdataPart01);
console.log("Test result: " + testResult02);
assert(testResult02 == 3);
obs.observe({ entryTypes: ["function"] });
const timerify_part2 = performance.timerify(part2);
console.log("RESULT: " + timerify_part2(readInputAsArray()));

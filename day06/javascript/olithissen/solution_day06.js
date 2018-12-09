#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const {
  performance,
  PerformanceObserver
} = require("perf_hooks");

const obs = new PerformanceObserver(list => {
  list.getEntries().forEach(item => console.log(item.name + ": " + item.duration));
  obs.disconnect();
});

/**
 * Reads an input file as an array of x-y-tuples
 */
function readInputAsArray(file) {
  function nextChar(c) {
    return String.fromCharCode(c.charCodeAt(0) + 1);
  }

  let char = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  let charPointer = 0;
  return fs
    .readFileSync(file, "utf8")
    .split("\r\n")
    .map(line => line.split(",").map(coord => parseInt(coord)))
    .map(line => {
      return {
        id: char.substr(++charPointer, 1),
        coord: line
      };
    });
}

function manhattanDistance(coord1, coord2) {
  return Math.abs(coord1[0] - coord2[0]) + Math.abs(coord1[1] - coord2[1]);
}

function getSizes(claims) {
  return claims
    .filter(coord => coord !== "disputed")
    .reduce((acc, cur) => {
      if (!acc[cur.id]) {
        acc[cur.id] = 1;
      } else {
        acc[cur.id]++;
      }
      return acc;
    }, {});
}

function getClaimsWithinBounds(minX, maxX, minY, maxY, data) {
  const claims = [];
  for (let x = minX; x <= maxX; x++) {
    for (let y = minY; y <= maxY; y++) {
      const closest = getClosestToCoordinate(x, y, data);
      claims.push(closest);
    }
  }
  return claims;
}

function getClosestToCoordinate(x, y, data) {
  const distances = getDistances(x, y, data);
  const indexOfShortest = Math.min(...Object.keys(distances));
  const closest = distances[indexOfShortest].length > 1 ? "disputed" : distances[indexOfShortest][0];
  return closest;
}

function getDistances(x, y, data) {
  const distances = {};
  const currentCoord = [x, y];
  for (coord of data) {
    const distance = manhattanDistance(currentCoord, coord.coord);
    if (!distances[distance]) {
      distances[distance] = [];
    }
    distances[distance].push(coord);
  }
  return distances;
}

/**
 * Solution for part 1
 *
 * @param  {} data Input data as array
 */
function part1(data) {
  const minX = Math.min(...data.map(coord => coord.coord[0]));
  const minY = Math.min(...data.map(coord => coord.coord[1]));
  const maxX = Math.max(...data.map(coord => coord.coord[0]));
  const maxY = Math.max(...data.map(coord => coord.coord[1]));

  const claims = getClaimsWithinBounds(minX, maxX, minY, maxY, data);
  const claimsExtent = getClaimsWithinBounds(minX - 1, maxX + 1, minY - 1, maxY + 1, data);

  const sizes = getSizes(claims);
  const sizesExtent = getSizes(claimsExtent);

  const finites = Object.keys(sizes).filter(item => {
    return sizes[item] === sizesExtent[item];
  });

  return Math.max(...finites.map(item => sizes[item]));
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data, maxDistance) {
  const minX = Math.min(...data.map(coord => coord.coord[0]));
  const minY = Math.min(...data.map(coord => coord.coord[1]));
  const maxX = Math.max(...data.map(coord => coord.coord[0]));
  const maxY = Math.max(...data.map(coord => coord.coord[1]));

  let results = [];
  for (let x = minX; x <= maxX; x++) {
    for (let y = minY; y <= maxY; y++) {
      let distances = getDistances(x, y, data);
      const absDistance = Object.keys(distances).map(dist => dist * distances[dist].length).reduce((a,c) => a += c);
      results.push({pos: [x, y], distance: absDistance});
    }
  }

  const filtered = results.filter(item => item.distance < maxDistance);

  console.log(filtered.length);
  return filtered.length;
}

/**
 * Runners, reference tests and performance data
 */

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");
// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 17);

obs.observe({entryTypes: ["function"]});
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(realData01);
assert(realResult01 == 3251, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult01);

// Part 2
const testResult02 = part2(testdataPart01, 32);
console.log("Test result: " + testResult02);
assert(testResult02 == 16);

obs.observe({ entryTypes: ["function"] });
const timerify_part2 = performance.timerify(part2);
const realResult02 = timerify_part2(realData01, 10000);
assert(realResult02 == 47841, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult02);

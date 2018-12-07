#!/usr/bin/env node

const fs = require("fs");
const assert = require("assert");
const { performance, PerformanceObserver } = require("perf_hooks");

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

  let char = "A";

  return fs
    .readFileSync(file, "utf8")
    .split("\r\n")
    .map(line => line.split(",").map(coord => parseInt(coord)))
    .map(line => {
      char = nextChar(char);
      return { id: char, coord: line };
    });
}

function manhattanDistance(coord1, coord2) {
  return Math.abs(coord1[0] - coord2[0]) + Math.abs(coord1[1] - coord2[1]);
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

  const infinites = data.filter(
    coord =>
      coord.coord[0] == minX ||
      coord.coord[0] == maxX ||
      coord.coord[1] == minY ||
      coord.coord[1] == maxY
  );

  const claims = [];
  const owners = {};
  for (let x = minX; x <= maxX; x++) {
    for (let y = minY; y <= maxY; y++) {
      const distances = {};
      const currentCoord = [x, y];
      for (coord of data) {
        if (!distances[manhattanDistance(currentCoord, coord.coord)]) {
          distances[manhattanDistance(currentCoord, coord.coord)] = [];
        }
        distances[manhattanDistance(currentCoord, coord.coord)].push(coord);
      }

      const indexOfShortest = Math.min(...Object.keys(distances));

      owners[currentCoord] =
        distances[indexOfShortest].length > 1 ? { id: "." } : distances[indexOfShortest][0];

      claims.push(
        distances[indexOfShortest].length > 1 ? "disputed" : distances[indexOfShortest][0]
      );
    }
  }

  visualize(owners);

  const finites = claims
    .filter(coord => coord !== "disputed")
    .filter(coord => infinites.indexOf(coord) === -1)
    .reduce((acc, cur) => {
      if (!acc[cur.id]) {
        acc[cur.id] = 1;
      } else {
        acc[cur.id]++;
      }
      return acc;
    }, {});

  console.log(JSON.stringify(finites));

  return Math.max(...Object.values(finites));
}

function visualize(grid) {
  const data = Object.keys(grid).map(item => item.split(","));
  const minX = Math.min(...data.map(coord => coord[0]));
  const minY = Math.min(...data.map(coord => coord[1]));
  const maxX = Math.max(...data.map(coord => coord[0]));
  const maxY = Math.max(...data.map(coord => coord[1]));

  let string = "";
  for (let y = minY; y <= maxY; y++) {
    for (let x = minX; x <= maxX; x++) {
      string += grid[[x, y]].id;
    }
    string += "\r\n";
  }
  fs.writeFileSync("out.txt", string);
}

/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {
  return 0;
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

obs.observe({ entryTypes: ["function"] });
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(realData01);
// assert(realResult01 == 10450, "Good job, you broke a working solution ... ");
console.log("RESULT: " + realResult01);

// Part 2
// const testResult02 = part2(testdataPart01);
// console.log("Test result: " + testResult02);
// assert(testResult02 == 4);

// obs.observe({ entryTypes: ["function"] });
// const timerify_part2 = performance.timerify(part2);
// const realResult02 = timerify_part2(realData01);
// assert(realResult02 == 4624, "Good job, you broke a working solution ... ");
// console.log("RESULT: " + realResult02);

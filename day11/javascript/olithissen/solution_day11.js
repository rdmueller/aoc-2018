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
 * Reads an input file as an array by splitting it  char by char
 */
function readInputAsArray(file) {
  return fs.readFileSync(file, "utf8").split("\r\n");
}

function calculatePowerForCell(x, y, sn) {
  const rackId = x + 10;
  let powerLevel = (rackId * y + sn) * rackId;
  const hundred = Math.floor(powerLevel / 100) % 10;
  return hundred - 5;
}

function subgridPower(grid, size) {
  for (let x = 1; x <= 298; x++) {
    for (let y = 1; y <= 298; y++) {
      let sum = 0;
      sum += grid[x + 0][y + 0].power;
      sum += grid[x + 0][y + 1].power;
      sum += grid[x + 0][y + 2].power;
      sum += grid[x + 1][y + 0].power;
      sum += grid[x + 1][y + 1].power;
      sum += grid[x + 1][y + 2].power;
      sum += grid[x + 2][y + 0].power;
      sum += grid[x + 2][y + 1].power;
      sum += grid[x + 2][y + 2].power;

      grid[x][y].subPower = sum;
    }
  }
}

function populateGrid(sn) {
  let grid = [];
  for (let x = 1; x <= 300; x++) {
    grid[x] = [];
    for (let y = 1; y <= 300; y++) {
      grid[x][y] = {
        power: calculatePowerForCell(x, y, sn)
      };
    }
  }
  return grid;
}

function part1(sn) {
  let grid = populateGrid(sn);
  subgridPower(grid);

  let max = 0;
  let coordinates = {};
  for (let x = 1; x <= 300; x++) {
    for (let y = 1; y <= 300; y++) {
      if (grid[x][y].subPower > max) {
        max = grid[x][y].subPower;
        coordinates = {
          x: x,
          y: y
        };
      }
    }
  }

  return coordinates;
}


/**
 * Solution for part 2
 *
 * @param  {} data Input data as array
 */
function part2(data) {}

/**
 * Runners, reference tests and performance data
 */

// const testdataPart01 = readInputAsArray("testdata.txt");
// const realData01 = readInputAsArray("input.txt");

// Part 1
assert(calculatePowerForCell(3, 5, 8) == 4);
assert(calculatePowerForCell(122, 79, 57) == -5);
assert(calculatePowerForCell(217, 196, 39) == 0);
assert(calculatePowerForCell(101, 153, 71) == 4);
// const testResult01 = part1(9221);
// console.log("Test result: " + testResult01);
// assert(testResult01 == 2);


obs.observe({
  entryTypes: ["function"]
});
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(9221);
console.log("RESULT: " + realResult01);
assert(realResult01.x === 20 && realResult01.y === 77, "Good job, you broke a working solution ... ");

// Part 2
// const testResult02 = part1(testdataPart02);
// console.log("Test result: " + testResult02);
// assert(testResult02 == 2);

// obs.observe({
//   entryTypes: ["function"]
// });
// const timerify_part2 = performance.timerify(part2);
// const realResult02 = timerify_part2(realData01);
// console.log("RESULT: " + realResult02);
// assert(realResult02 == 10240, "Good job, you broke a working solution ... ");
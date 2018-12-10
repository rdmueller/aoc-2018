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
  return fs.readFileSync(file, "utf8")
    .split("\r\n")
    .map(line => {
      // position=< 9,  1> velocity=< 0,  2>
      const result = line.split(/position=<(.*?),(.*?)> velocity=<(.*?),(.*?)>/).filter(item => item !== '').map(item => parseInt(item));
      return result;
    })
    .map(item => {
      return {
        x: item[0],
        y: item[1],
        dx: item[2],
        dy: item[3],
        move: function () {
          this.x += this.dx;
          this.y += this.dy;
        }
      };
    });
}

function render(data) {
  const maxX = Math.max(...data.map(item => item.x));
  const maxY = Math.max(...data.map(item => item.y));
  const minX = Math.min(...data.map(item => item.x));
  const minY = Math.min(...data.map(item => item.y));

  //If the rows are more then 10 apart, there is no use in rendering them 
  if (maxY - minY > 10) {
    return "";
  }
  
  let output = "";
  for (let row = minY; row <= maxY; row++) {
    for (let col = minX; col <= maxX; col++) {
      if (data.filter(item => item.x === col && item.y === row).length > 0) {
        output += "#";
      } else {
        output += ".";
      }
    }
    output += "\r\n";
  }

  return output;
}

function sleep(ms) {
  return new Promise(resolve => {
    setTimeout(resolve, ms)
  })
}

function part1(data) {
  fs.appendFileSync('output.txt', render(data));
  let cnt = 0;
  while (true) {
    cnt++;
    data.forEach(item => item.move());
    let renderResult = render(data);
    if (renderResult.length > 0) {
      fs.appendFileSync('output.txt', cnt + "\r\n" + render(data));
      // It's improbable that a there is more than one plausible result. Break.
      break;
    }
  }

  return cnt;
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

const testdataPart01 = readInputAsArray("testdata.txt");
const realData01 = readInputAsArray("input.txt");

// Part 1
const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 2);

obs.observe({
  entryTypes: ["function"]
});
const timerify_part1 = performance.timerify(part1);
const realResult01 = timerify_part1(realData01);
console.log("RESULT: " + realResult01);
assert(realResult01 == 10240, "Good job, you broke a working solution ... ");
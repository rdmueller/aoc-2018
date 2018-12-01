#!/usr/bin/env node

var fs = require('fs');

function readInputAsArray() {
    return fs.readFileSync('./input.txt', 'utf8').split("\r\n");
}

// Solution part 1 using map-reduce
function part1() {
    var result = readInputAsArray()
        .map(s => parseInt(s))
        .reduce((a, c) => a + c);

    console.log("RESULT: " + result);
}

// Solution part 2 using a caveman-like loop
function part2() {
    var input = fs.readFileSync('./input.txt', 'utf8').split("\r\n");
    var currentFrequency = 0;
    var knownFrequencies = [0];
    var i = 0;

    while(i < input.length) {
        currentFrequency += parseInt(input[i]);
        knownFrequencies.push(currentFrequency);

        i = (i + 1) % input.length;

        if (knownFrequencies.slice(0, -1).includes(currentFrequency)) {
            break;
        }
    }

    console.log("RESULT:" + currentFrequency);
}

part1();
part2();
#!/usr/bin/env node

var fs = require('fs');
var assert = require('assert');

function readInputAsArray() {
    return fs.readFileSync('input.txt', 'utf8').split("\r\n");
}

function intersectionDetection(claim, check) { // :)
    return !(check.left >= claim.right ||
        check.right <= claim.left ||
        check.top >= claim.bottom ||
        check.bottom <= claim.top);
}

function createFromCoordinate(left, top) {
    return {
        'left': left,
        'top': top,
        'width': 1,
        'height': 1,
        'right': left + 1,
        'bottom': top + 1
    }
}

function extentReducer() {
    return (acc, cur) => {
        if (cur.right > acc.width) {
            acc.width = cur.right;
        }
        if (cur.bottom > acc.height) {
            acc.height = cur.bottom;
        }
        return acc;
    };
}

function mapClaims() {
    return line => {
        let result = line.split(/#([0-9]+) \@ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)/);
        return {
            'id': parseInt(result[1]),
            'left': parseInt(result[2]),
            'top': parseInt(result[3]),
            'width': parseInt(result[4]),
            'height': parseInt(result[5]),
            'right': parseInt(result[2]) + parseInt(result[4]),
            'bottom': parseInt(result[3]) + parseInt(result[5]),
        };
    };
}

// Solution part 1
function part1(data) {
    let claims = data.map(mapClaims());

    let extents = claims.reduce(extentReducer(), {
        'width': 0,
        'height': 0
    });

    let intersections = 0;

    for (let i = 0; i < extents.width + 1; i++) {
        for (let j = 0; j < extents.height + 1; j++) {
            const check = createFromCoordinate(i, j);
            const hits = claims.filter(claim => {
                return intersectionDetection(claim, check);
            });
            if (hits.length >= 2) {
                intersections++;
            }
        }
    }

    return intersections;
}

// Solution part 2
function part2(data) {
    let claims = data.map(mapClaims());

    let acc = {};

    for (let i = 0; i < claims.length; i++) {
        for (let j = i + 1; j < claims.length; j++) {
            const intersects = intersectionDetection(claims[i], claims[j]);
            if (intersects) {
                acc.hasOwnProperty(claims[i].id) ? acc[claims[i].id]++ : (acc[claims[i].id] = 1);
                acc.hasOwnProperty(claims[j].id) ? acc[claims[j].id]++ : (acc[claims[j].id] = 1);
            }
        }
    }

    let result = claims.filter(claim => {
        return !acc.hasOwnProperty(claim.id);
    })

    return result[0].id;
}

const testdataPart01 = [
    "#1 @ 1,3: 4x4",
    "#2 @ 3,1: 4x4",
    "#3 @ 5,5: 2x2",
]

const testResult01 = part1(testdataPart01);
console.log("Test result: " + testResult01);
assert(testResult01 == 4);
console.log("RESULT:" + part1(readInputAsArray()));

const testResult02 = part2(testdataPart01)
console.log("Test result: " + testResult02);
assert(testResult02 == 3);
console.log("RESULT: " + part2(readInputAsArray()));
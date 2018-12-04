#!/usr/bin/env ts-node

import * as fs from 'fs';

const inputFile = './input.txt'
const input: string = fs.readFileSync(inputFile, "utf8");

function solve(input: string) {
    let values = input.split(/\r?\n/);
    let result = 1;
    let stat = {};
    [2, 3].forEach(n => {
        values.forEach(elem => {
            let found = false
            for (let letter of elem) {
                if ((elem.match(new RegExp(letter, 'g')) || []).length == n) {
                    found = true
                }
            }
            if(found) {
                stat[n] = (stat[n] || 0) + 1;
            }
        })
        result *= stat[n];
    });
    console.log(stat);
    console.log(result);
}

solve(input);


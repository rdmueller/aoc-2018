#!/usr/bin/env ts-node

import * as fs from 'fs';

const inputFile = './input.txt'
const input: string = fs.readFileSync(inputFile, "utf8");

function solve(input: string) {
    let values = input.split(/\r?\n/);
    values.sort();

    let n = values.length;
    let result
    for (let i = 0; i < n-1; i++) {
        for (let j = i+1; j < n; j++) {
        const word1 = values[i];
        const word2 = values[j];
        let distance = word1.length
        result = "";
        for (let k = 0; k < word1.length; k++) {
            if(word1[k] === word2[k]) {
                distance--;
                result +=word1[k];
            }
        }
        if(distance === 1) {
            console.log(`w1: ${word1} | w2: ${word2} | distance: ${distance}`);
            console.log(`result: ${result}`);
            return
        }

    }

    }
}

new RegExp("");

solve(input);

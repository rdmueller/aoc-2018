#!/usr/bin/env ts-node

import * as fs  from 'fs';

const inputFile = './input.txt'
const input : string = fs.readFileSync(inputFile, "utf8");

function solve(input:string){
    let result
    let lines = input.split(/\r?\n/)
    result = lines.map(it => Number(it)).reduce((a, b) => a + b, 0);;
    console.log(result);
}


solve(input);


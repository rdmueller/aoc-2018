#!/usr/bin/env ts-node

//import { readFileSync } from 'fs';
import * as fs from 'fs';

const inputFile = './input.txt'
const input: string = fs.readFileSync(inputFile, "utf8");

function solve(input: string) {
    let values = input.split(/\r?\n/).map(it => Number(it));
    let frequency : number = 0;
    let frequencies = [];
    for (let n = 0; n < 1000; n++) {
        values.forEach((elem,i)=>{
            frequency +=elem;
            if(frequencies.indexOf(frequency)>-1){
                console.log(frequency);
                console.log(n);
                process.exit(0);
            }
            frequencies.push(frequency);
        })
    }
    console.log("finished without duplicates");
}

solve(input);

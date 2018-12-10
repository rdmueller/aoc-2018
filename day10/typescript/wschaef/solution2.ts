#!/usr/bin/env ts-node

import * as fs from 'fs';
import { createClient } from 'http';

const inputFile = './input.txt'
const input: string = fs.readFileSync(inputFile, "utf8");

function solve(input: string) {
    let lines = input.split(/\r?\n/);
    let matrix = lines.map(it=>it
            .replace("position=<","")
            .replace("> velocity=<",",")
            .replace(">","")
            .trim()
            .split(",").
            map(e=>Number(e)));

    let height1 = getHeight(matrix);
    let height2 = getHeight(matrix);

    for (let i = 0; i < 1000000; i++) {
        if(height2 > height1){
            matrix = matrix.map(it=>transformBack(it));
            console.log(i-1);
            break;
        }else{
            height1 = height2;
            matrix = matrix.map(it=>transform(it));
            height2 = getHeight(matrix);
        }
    }
    print(matrix);
}

function getHeight(matrix: Array<number[]>): number{
    let xValues = matrix.map(it=>it[0]);
    return Math.max(...xValues) - Math.min(...xValues);
}

function transform(point: Array<number>): Array<number>{
    point[0] += point[2];
    point[1] += point[3];
    return point;
}

function transformBack(point: Array<number>): Array<number>{
    point[0] -= point[2];
    point[1] -= point[3];
    return point;
}

function print(matrix: Array<number[]>){
    let xValues = matrix.map(it=>it[0]);
    let yValues = matrix.map(it=>it[1]);
    let xMinMax = [Math.min(...xValues),Math.max(...xValues)];
    let yMinMax = [Math.min(...yValues),Math.max(...yValues)];
    for (let y = yMinMax[0]; y <= yMinMax[1]; y++) {
        let line = ""
        for (let x = xMinMax[0]; x <= xMinMax[1]; x++) {
            let point = matrix.find(it=>it[0]==x && it[1]==y) ? "#" : ".";
            line += point;
        }
        console.log(line);
    }

}

solve(input);


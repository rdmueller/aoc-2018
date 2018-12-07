#!/usr/bin/env ts-node

import * as fs from 'fs';
import { createClient } from 'http';

const inputFile = './input.txt'
const input: string = fs.readFileSync(inputFile, "utf8");

function solve(input: string) {
    let values = input.split(/\r?\n/);
    let points: Array<Point> = values.map(it => new Point().parse(it));

    //compute left top and right bottom points
    let xValues = points.map(it => it.x);
    let yValues = points.map(it => it.y);
    let p0 = new Point(Math.min(...xValues), Math.min(...yValues));
    let pN = new Point(Math.max(...xValues), Math.max(...yValues));

    //define the screen from p0 to pN
    let screen: Array<Point> = [];
    for (let i = p0.x; i <= pN.x; i++) {
        for (let j = p0.y; j <= pN.y; j++) {
            screen.push(new Point(i, j));
        }
    }

    // create matrix for each screenpoint the distance to neares point
    let matrix : [Point,[Point,number]][] = screen.map(it =>
        [
            it,
            points.map(p =>
                [
                    p,
                    p.distance(it)
                ])
                .reduce(function(a, b){
                    if(a[1] < b[1]) return a;
                    if(a[1] > b[1]) return b;
                    if(a[1] == b[1]) return [null,a[1]];
                })
        ]as [Point,[Point,number]]
    );

    //compute infinite points
    let infinitePoints = [... new Set(
        matrix.filter(it=>[p0.x,pN.x].includes(it[0].x) ||[p0.y,pN.y].includes( it[0].y)).map(it=>it[1][0])
        )].filter(it => it !== null);

    //get all screen points not in area of the infinitePoints
    let finiteScreenPoints = matrix.filter(it => it[1][0] !==null && !infinitePoints.includes(it[1][0]))

    // for every point count finiteScreenPoints
    let result = points.map(function(p){
        return finiteScreenPoints.filter(it=>it[1][0] === p).length
    });
    console.log(Math.max(...result));

}

class Point {
    public x: number;
    public y: number;
    constructor(x?: number, y?: number) {
        x ? this.x = x : 0;
        y ? this.y = y : 0;
    }
    parse(tupel: string) {
        this.x = Number(tupel.split(",")[0]);
        this.y = Number(tupel.split(",")[1]);
        return this;
    }

    distance(p: Point): number {
        return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
    }
}

solve(input);


#!/usr/bin/env ts-node

import fs = require('fs');

const inputFile = './input.txt'
let input : string = fs.readFileSync(inputFile, "utf8");


console.log(input);

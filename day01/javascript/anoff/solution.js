#!/usr/bin/env node

const fs = require('fs')

const content = fs.readFileSync('./input.txt', 'utf8')

// Part 1
const endFrequency = content
  .split('\n')
  .map(e => parseInt(e))
  .filter(e => !isNaN(e))
  .reduce((p, c) => p + c, 0)

console.log(`Part 1 solution: ${endFrequency}`)

// Part 2
const knownFrequencies = new Array(0)
let frequency = 0
const list = content
  .split('\n')
  .map(e => parseInt(e))
  .filter(e => !isNaN(e))

for (let iter = 0; iter < 1000; iter++) {
  list.forEach((e, i) => {
    frequency += e
    if (knownFrequencies.indexOf(frequency) > -1) {
      console.log(`Part 2 solution: ${frequency} after ${iter} iterations`)
      process.exit(0)
    }
    knownFrequencies.push(frequency)
  })
}

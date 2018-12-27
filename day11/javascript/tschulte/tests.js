#!/usr/bin/env node

"use strict";

const assert = require("assert");
const { FuelCell, PowerGrid, SubPowerGrid } = require("./part1");
const { findHighestSubGrid } = require("./part2");

assert.equal(new FuelCell(1, 1, 8).rackId, 11);
assert.equal(new FuelCell(1, 2, 8).rackId, 11);
assert.equal(new FuelCell(2, 1, 8).rackId, 12);
assert.equal(new FuelCell(2, 2, 8).rackId, 12);

assert.equal(new FuelCell(3, 5, 8).powerLevel, 4);
assert.equal(new FuelCell(122, 79, 57).powerLevel, -5);
assert.equal(new FuelCell(217, 196, 39).powerLevel, 0);
assert.equal(new FuelCell(101, 153, 71).powerLevel, 4);

assert.equal(new FuelCell(33, 45, 18).powerLevel, 4);

const powerGrid18 = new PowerGrid(18);
assert.equal(powerGrid18.racks.length, 300);
assert.equal(powerGrid18.racks[1].length, 300);
assert.equal(powerGrid18.racks[0][1].x, 1);
assert.equal(powerGrid18.racks[0][1].y, 2);
assert.equal(powerGrid18.racks[32][44].powerLevel, 4);

const powerGrid42 = new PowerGrid(42);
assert.equal(powerGrid42.racks[20][60].powerLevel, 4);

assert.equal(powerGrid42.upperLeft.x, 1);
assert.equal(powerGrid42.upperLeft.y, 1);

assert.equal(new SubPowerGrid(powerGrid18, 31, 43, 1).powerLevel, -2);
assert.equal(new SubPowerGrid(powerGrid18, 31, 43, 1).upperLeft.x, 32);
assert.equal(new SubPowerGrid(powerGrid18, 31, 43, 1).upperLeft.y, 44);
assert.equal(new SubPowerGrid(powerGrid18, 32, 44, 1).powerLevel, 4);
assert.equal(new SubPowerGrid(powerGrid18, 32, 44).powerLevel, 29);

const highestPowerGrid18 = powerGrid18.findHighestSubGrid();

assert.equal(highestPowerGrid18.powerLevel, 29);
assert.equal(highestPowerGrid18.upperLeft.x, 33);
assert.equal(highestPowerGrid18.upperLeft.y, 45);

const highestPowerGrid42 = powerGrid42.findHighestSubGrid();

assert.equal(highestPowerGrid42.powerLevel, 30);
assert.equal(highestPowerGrid42.upperLeft.x, 21);
assert.equal(highestPowerGrid42.upperLeft.y, 61);

const highestPowerGrid18Any = findHighestSubGrid(powerGrid18);
assert.equal(highestPowerGrid18Any.powerLevel, 113);
assert.equal(highestPowerGrid18Any.upperLeft.x, 90);
assert.equal(highestPowerGrid18Any.upperLeft.y, 269);

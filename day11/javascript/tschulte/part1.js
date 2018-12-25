"use strict";

// tag::PowerGrid[]
class PowerGrid {
  constructor(serialNumber, size = 300) {
    this.racks = new Array(size);
    for (let x = 0; x < size; x++) {
      this.racks[x] = new Array(size);
      const cellX = x + 1;
      for (let y = 0; y < size; y++) {
        const cellY = y + 1;
        this.racks[x][y] = new FuelCell(cellX, cellY, serialNumber);
      }
    }
  }

  get upperLeft() {
    return this.racks[0][0];
  }

  findHighestSubGrid(size = 3) {
    const gridCount = Math.pow(this.racks.length - size, 2);
    const split = new Array(gridCount);
    const upperBound = this.racks.length - size;
    let highestSubGrid;
    for (let x = 0; x < upperBound; x++) {
      for (let y = 0; y < upperBound; y++) {
        const subGrid = new SubPowerGrid(this, x, y, size);
        if (!highestSubGrid || subGrid.powerLevel > highestSubGrid.powerLevel)
          highestSubGrid = subGrid;
      }
    }
    return highestSubGrid;
  }
}
// end::PowerGrid[]

// tag::SubPowerGrid[]
class SubPowerGrid {
  constructor(parentGrid, x, y, size = 3) {
    this.parentGrid = parentGrid;
    this.x = x;
    this.y = y;
    this.size = size;
  }

  get upperLeft() {
    return this.parentGrid.racks[this.x][this.y];
  }

  get powerLevel() {
    let powerLevel = 0;
    for (let x = this.size - 1; x >= 0; x--) {
      const parentX = this.x + x;
      for (let y = this.size - 1; y >= 0; y--) {
        const parentY = this.y + y;
        powerLevel += this.parentGrid.racks[parentX][parentY].powerLevel;
      }
    }
    return powerLevel;
  }
}
// end::SubPowerGrid[]

// tag::FuelCell[]
class FuelCell {
  constructor(x, y, gridSerialNumber) {
    this.x = x;
    this.y = y;
    this.gridSerialNumber = gridSerialNumber;

    let pl = this.rackId * this.y;
    pl += this.gridSerialNumber;
    pl *= this.rackId;
    pl %= 1000;
    pl = Math.floor(pl / 100);
    pl -= 5;
    this.powerLevel = pl;
  }
  get rackId() {
    return this.x + 10;
  }
}
// end::FuelCell[]

module.exports = {
  FuelCell,
  PowerGrid,
  SubPowerGrid
};

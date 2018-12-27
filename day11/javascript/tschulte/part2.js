"use strict";

function findHighestSubGrid(powerGrid) {
  let highestSubGrid;
  for (let i = powerGrid.size; i > 0; i--) {
    console.log(`Checking ${i}`);
    const subGrid = powerGrid.findHighestSubGrid(i);
    if (!highestSubGrid || subGrid.powerLevel > highestSubGrid.powerLevel)
      highestSubGrid = subGrid;
  }
  return highestSubGrid;
}

module.exports = {
  findHighestSubGrid
};

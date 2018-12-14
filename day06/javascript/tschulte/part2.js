"use strict";

const { areaOfCoords, distance } = require("./part1");

function findAreaWithin(coords, threashold) {
  const area = areaOfCoords(coords);
  let areaCount = 0;
  for (let x = area.minX; x <= area.maxX; x++) {
    for (let y = area.minY; y <= area.maxY; y++) {
      const xy = [x, y];
      const distanceToAllCoords = coords
        .map(coord => distance(xy, coord))
        .reduce((sum, current) => sum + current, 0);
      if (distanceToAllCoords < threashold) {
        areaCount++;
      }
    }
  }
  return areaCount;
}

module.exports = {
  findAreaWithin
};

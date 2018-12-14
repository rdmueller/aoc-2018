"use strict";

function parseLine(line) {
  return line.split(",").map(Number);
}

function areaOfCoords(coords) {
  return coords.reduce(
    (acc, coord) => {
      if (acc.minX === undefined || acc.minX > coord[0]) acc.minX = coord[0];
      if (acc.maxX === undefined || acc.maxX < coord[0]) acc.maxX = coord[0];
      if (acc.minY === undefined || acc.minY > coord[1]) acc.minY = coord[1];
      if (acc.maxY === undefined || acc.maxY < coord[1]) acc.maxY = coord[1];
      return acc;
    },
    { minX: undefined, maxX: undefined, minY: undefined, maxX: undefined }
  );
}

function isInfinite(area, coord) {
  return (
    coord[0] === area.minX ||
    coord[0] === area.maxX ||
    coord[1] === area.minY ||
    coord[1] === area.maxY
  );
}

function distance(coord1, coord2) {
  return Math.abs(coord1[0] - coord2[0]) + Math.abs(coord1[1] - coord2[1]);
}

function findLargestArea(coords) {
  const area = areaOfCoords(coords);
  const areaCounts = coords.map(coord => 0);
  for (let x = area.minX; x <= area.maxX; x++) {
    for (let y = area.minY; y <= area.maxY; y++) {
      const xy = [x, y];
      const distancesToXY = coords
        .map((coord, index) => {
          return { index: index, distance: distance(xy, coord) };
        })
        .sort((a, b) => a.distance - b.distance);
      if (distancesToXY[0].distance !== distancesToXY[1].distance) {
        areaCounts[distancesToXY[0].index]++;
      }
    }
  }
  return areaCounts
    .map((count, index) => (isInfinite(area, coords[index]) ? -1 : count))
    .sort((a, b) => b - a)[0];
}

module.exports = {
  parseLine,
  areaOfCoords,
  isInfinite,
  distance,
  findLargestArea
};

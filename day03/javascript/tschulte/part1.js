"use strict";

// tag::parseLine[]
exports.parseLine = function(line) {
  const rectangle = {};
  const split = line.split(/[@,:x]/).map(x => x.trim());
  rectangle.id = split[0].substring(1);
  rectangle.x = Number(split[1]);
  rectangle.y = Number(split[2]);
  rectangle.width = Number(split[3]);
  rectangle.height = Number(split[4]);
  return rectangle;
};
// end::parseLine[]

// tag::intersection[]
exports.intersection = function(rect1, rect2) {
  const x = Math.max(rect1.x, rect2.x);
  const width = Math.min(rect1.x + rect1.width, rect2.x + rect2.width) - x;
  const y = Math.max(rect1.y, rect2.y);
  const height = Math.min(rect1.y + rect1.height, rect2.y + rect2.height) - y;
  if (width > 0 && height > 0) {
    return {
      ids: [rect1.id, rect2.id],
      x: x,
      y: y,
      width: width,
      height: height
    };
  }
  return null;
};
// end::intersection[]

// tag::intersections[]
exports.intersections = function(rects) {
  const intersections = [];
  for (let i = 0; i < rects.length - 1; i++) {
    for (let j = i + 1; j < rects.length; j++) {
      let intersection = exports.intersection(rects[i], rects[j]);
      if (intersection) {
        intersections.push(intersection);
      }
    }
  }
  return intersections;
};
// end::intersections[]

// tag::intersectingInches[]
exports.intersectingInches = function(intersections) {
  const map = {};
  let intersectingInches = 0;
  intersections.forEach(rect => {
    for (let x = rect.x; x < rect.x + rect.width; x++) {
      for (let y = rect.y; y < rect.y + rect.height; y++) {
        const key = "" + x + "/" + y;
        if (!map[key]) {
          map[key] = "processed";
          intersectingInches++;
        }
      }
    }
  });
  return intersectingInches;
};
// end::intersectingInches[]

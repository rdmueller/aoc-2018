"use strict";

// tag::parseLine[]
function parseLine(line) {
  const values = line
    .split(/position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>/)
    .filter(values => values)
    .map(Number);
  return new Light(...values);
}
// end::parseLine[]

// tag::Light[]
class Light {
  constructor(x, y, vx, vy) {
    this.x = x;
    this.y = y;
    this.vx = vx;
    this.vy = vy;
  }
  move() {
    return new Light(this.x + this.vx, this.y + this.vy, this.vx, this.vy);
  }
}
// end::Light[]

// tag::Lights[]
class Lights {
  constructor(lights, time = 0) {
    this.lights = lights;
    this.time = time;
    const min = (min, val) => (val < min ? val : min);
    const max = (max, val) => (val > max ? val : max);
    const x = lights.map(val => val.x);
    const y = lights.map(val => val.y);
    this.minX = x.reduce(min, Number.MAX_VALUE);
    this.maxX = x.reduce(max, Number.MIN_VALUE);
    this.minY = y.reduce(min, Number.MAX_VALUE);
    this.maxY = y.reduce(max, Number.MIN_VALUE);
  }
  get width() {
    return 1 + this.maxX - this.minX;
  }
  get height() {
    return 1 + this.maxY - this.minY;
  }
  move() {
    return new Lights(this.lights.map(light => light.move()), this.time + 1);
  }
  moveUntilMessageAppears() {
    const nextLights = this.move();
    if (nextLights.width < this.width || nextLights.height < this.height) {
      return nextLights.moveUntilMessageAppears();
    }
    return this;
  }
  toString() {
    let string = "";
    for (let y = this.minY; y <= this.maxY; y++) {
      const xValues = this.lights
        .filter(light => light.y === y)
        .map(light => light.x);
      for (let x = this.minX; x <= this.maxX; x++) {
        string += xValues.indexOf(x) != -1 ? "#" : ".";
      }
      string += "\n";
    }
    return string;
  }
}
// end::Lights[]

module.exports = {
  parseLine,
  Lights
};

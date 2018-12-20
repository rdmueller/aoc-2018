"use strict";

// tag::parseLine[]
function parseLine(line) {
  const parsed = line
    .split(/(\d+) players; last marble is worth (\d+) points/)
    .filter(s => s.length > 0)
    .map(Number);
  return new MarbleGame(...parsed);
}
// end::parseLine[]

// tag::MarbleGame[]
class MarbleGame {
  constructor(playerCount, lastMarble) {
    this.players = new Array(playerCount).fill(0);
    this.circle = new Circle();
    for (let i = 0; i < lastMarble; i++) {
      this.players[i % playerCount] += this.circle.addNextMarble();
    }
  }

  get highscore() {
    return this.players.reduce(
      (highscore, score) => (score > highscore ? score : highscore),
      0
    );
  }
}
// end::MarbleGame[]

// tag::Circle[]
class Circle {
  constructor() {
    this.firstMarble = new CircleMarble(0);
    this.currentMarble = this.firstMarble;
    this.lastAddedMarble = 0;
  }

  /** Only for tests */
  get marbles() {
    const marbles = [];
    let i = this.firstMarble;
    while (true) {
      marbles.push(i.value);
      i = i.next;
      if (i == this.firstMarble) return marbles;
    }
    return marbles;
  }

  /**
   * Add the next marble, return the points of that step
   */
  addNextMarble() {
    const nextMarble = this.lastAddedMarble + 1;
    this.lastAddedMarble = nextMarble;
    if (nextMarble % 23 === 0) {
      const removed = this.currentMarble.previousN(7);
      this.currentMarble = removed.next;
      removed.remove();
      return nextMarble + removed.value;
    } else {
      this.currentMarble = this.currentMarble.nextN(1).insertAfter(nextMarble);
      return 0;
    }
  }
  checkIndex(index) {
    return index % this.marbles.length;
  }
}
// end::Circle[]

class CircleMarble {
  constructor(value) {
    this.value = value;
    this.previous = this;
    this.next = this;
  }
  /**
   * Get the n-th next item
   */
  nextN(n) {
    if (n === 0) return this;
    return this.next.nextN(n - 1);
  }
  /**
   * Get the n-th previous item
   */
  previousN(n) {
    if (n === 0) return this;
    return this.previous.previousN(n - 1);
  }
  /** insert a marble after this CircleMarble. Returns the new CircleMarble. */
  insertAfter(marble) {
    const circleMarble = new CircleMarble(marble);
    circleMarble.next = this.next;
    circleMarble.previous = this;
    this.next.previous = circleMarble;
    this.next = circleMarble;
    return circleMarble;
  }
  /** remove this CircleMarble from the circle */
  remove() {
    this.next.previous = this.previous;
    this.previous.next = this.next;
    this.previous = this;
    this.next = this;
  }
}

module.exports = {
  parseLine,
  MarbleGame,
  Circle,
  CircleMarble
};

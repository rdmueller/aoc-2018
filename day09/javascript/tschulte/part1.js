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
  constructor(
    marbles = [0],
    currentMarble = 0,
    lastAddedMarble = currentMarble
  ) {
    this.marbles = marbles;
    this.currentIndex = marbles.indexOf(currentMarble);
    this.lastAddedMarble = lastAddedMarble;
  }

  /** get the current marble for tests */
  get currentMarble() {
    return this.marbles[this.currentIndex];
  }

  /**
   * Add the next marble, return the points of that step
   */
  addNextMarble() {
    const nextMarble = this.lastAddedMarble + 1;
    this.lastAddedMarble = nextMarble;
    if (nextMarble % 23 === 0) {
      const index = this.checkIndex(this.currentIndex - 7);
      const removed = this.marbles.splice(index, 1);
      this.currentIndex = this.checkIndex(index);
      return nextMarble + removed[0];
    } else {
      const index = this.checkIndex(this.currentIndex + 2);
      this.marbles.splice(index, 0, nextMarble);
      this.currentIndex = index;
      return 0;
    }
  }
  checkIndex(index) {
    return index % this.marbles.length;
  }
}
// end::Circle[]

module.exports = {
  parseLine,
  MarbleGame,
  Circle
};

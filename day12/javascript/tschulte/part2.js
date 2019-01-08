"use strict";

function parseLine(line) {
  return line;
}

class Pots {
  constructor(initialState, rules) {
    const pots = initialState
      .replace("initial state: ", "")
      .split("")
      .map((state, index) => new Pot(index, state));
    this.firstPot = pots[0];
    this.lastPot = pots.reduce((next, curr) => {
      curr.prev = next;
      return curr;
    });
    this.ensureExactlyOneEmptyOuterPot();
    this.rules = {};
    rules.forEach(rule => {
      const [key, value] = rule.split(/ => /);
      this.rules[key] = value;
    });
    this.gen = 0;
    this.numbers = this.addNumbers();
    this.previousNumbers = 0;
  }

  warmup() {
    let prevToString = "";
    while (prevToString !== this.toString()) {
      prevToString = this.toString();
      this.nextGen();
    }
  }

  get numbersDiff() {
    return this.numbers - this.previousNumbers;
  }

  ensureExactlyOneEmptyOuterPot() {
    while (this.firstPot.state === ".") {
      this.firstPot = this.firstPot.next;
    }
    this.firstPot.prev = new Pot(this.firstPot.id - 1, ".");
    this.firstPot = this.firstPot.prev;
    while (this.lastPot.state === ".") {
      this.lastPot = this.lastPot.prev;
    }
    this.lastPot.next = new Pot(this.lastPot.id + 1, ".");
    this.lastPot = this.lastPot.next;
  }

  nextGen() {
    const newFirstPot = new Pot(
      this.firstPot.id,
      this.nextGenValue(this.firstPot)
    );

    let pot = this.firstPot;
    let prev = newFirstPot;
    while ((pot = pot.next)) {
      const newPot = this.nextGenPot(pot);
      prev.next = newPot;
      prev = newPot;
    }

    this.firstPot = newFirstPot;
    this.lastPot = prev;
    this.ensureExactlyOneEmptyOuterPot();
    this.gen++;
    this.previousNumbers = this.numbers;
    this.numbers = this.addNumbers();
  }

  nextGenValue(pot) {
    const nextGenValue = this.rules[pot.toString()];
    if (nextGenValue) return nextGenValue;
    return ".";
  }

  nextGenPot(pot) {
    const newPot = new Pot(pot.id, this.nextGenValue(pot));
    return newPot;
  }

  pot(id) {
    return this.firstPot.find(id);
  }

  addNumbers() {
    let i = 0;
    let pot = this.firstPot;
    while ((pot = pot.next)) {
      i += pot.state === "#" ? pot.id : 0;
    }
    return i;
  }

  toString() {
    let s = this.firstPot.state;
    let pot = this.firstPot;
    while ((pot = pot.next)) {
      s += pot.state;
    }
    return s;
  }
}

class Pot {
  constructor(id, state, prev, next) {
    this.id = id;
    this.state = state;
    this.prevPot = prev;
    this.nextPot = next;
  }

  set prev(prev) {
    this.prevPot = prev;
    if (prev && prev.next != this) prev.next = this;
  }
  get prev() {
    return this.prevPot;
  }

  set next(next) {
    this.nextPot = next;
    if (next && next.prev != this) next.prev = this;
  }
  get next() {
    return this.nextPot;
  }

  stateString(pot) {
    if (!pot) return ".";
    return pot.state;
  }

  toString() {
    const prev = this.prev;
    const prevPrev = prev ? prev.prev : null;
    const next = this.next;
    const nextNext = next ? next.next : null;
    return (
      this.stateString(prevPrev) +
      this.stateString(prev) +
      this.stateString(this) +
      this.stateString(next) +
      this.stateString(nextNext)
    );
  }

  find(id) {
    if (id === this.id) return this;
    if (id > this.id && this.next) return this.next.find(id);
    if (id < this.id && this.prev) return this.prev.find(id);
    return null;
  }
}

module.exports = {
  parseLine,
  Pots,
  Pot
};

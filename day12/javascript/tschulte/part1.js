"use strict";

function parseLine(line) {
  return line;
}

class Pots {
  constructor(initialState, rules) {
    const pots = initialState
      .replace("initial state: ", "")
      .split("")
      .map(state => state === "#")
      .map((state, index) => new Pot(index, state));
    this.potZero = pots.reverse().reduce((next, curr) => {
      curr.next = next;
      return curr;
    });
    this.potZero.prev = new Pot(-1, false, null);
    this.pot(pots.length - 1).next = new Pot(pots.length, false);
    this.rules = {};
    rules.forEach(rule => {
      const [key, value] = rule.split(/ => /);
      this.rules[key] = value === "#";
    });
    this.gen = 0;
  }

  nextGen() {
    const newPotZero = new Pot(0, this.nextGenValue(this.potZero));
    let pot = this.potZero;
    let prev = newPotZero;
    while ((pot = pot.prev)) {
      const newPot = this.nextGenPot(pot);
      prev.prev = newPot;
      prev = newPot;
    }
    if (prev.state) {
      prev.prev = new Pot(prev.id - 1, false);
    }
    while (!prev.state && prev.next && !prev.next.state) {
      prev = prev.next;
      prev.prev = null;
    }
    pot = this.potZero;
    prev = newPotZero;
    while ((pot = pot.next)) {
      const newPot = this.nextGenPot(pot);
      prev.next = newPot;
      prev = newPot;
    }
    if (prev.state) {
      prev.next = new Pot(prev.id + 1, false);
    }
    while (!prev.state && prev.prev && !prev.prev.state) {
      prev = prev.prev;
      prev.next = null;
    }
    this.potZero = newPotZero;
    this.gen++;
  }

  nextGenValue(pot) {
    return this.rules[pot.toString()] === true;
  }

  nextGenPot(pot) {
    const newPot = new Pot(pot.id, this.nextGenValue(pot));
    return newPot;
  }

  pot(id) {
    return this.potZero.find(id);
  }

  addNumbers() {
    let i = 0;
    let pot = this.potZero;
    while ((pot = pot.next)) {
      i += pot.state ? pot.id : 0;
    }
    pot = this.potZero;
    while ((pot = pot.prev)) {
      i += pot.state ? pot.id : 0;
    }
    return i;
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
    return pot.state ? "#" : ".";
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

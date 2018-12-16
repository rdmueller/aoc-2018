"use strict";

class Step {
  constructor(id) {
    this.id = id;
    this.parents = [];
    this.children = [];
    this.hasRun = false;
  }

  dependsOn(step) {
    step.children.push(this);
    this.parents.push(step);
  }

  canRun() {
    return !this.hasRun && this.parents.every(p => p.hasRun);
  }

  run() {
    this.hasRun = true;
  }
}

function parseLine(steps, line) {
  const match = line
    .split(/Step (\w+) must be finished before step (\w+) can begin./gm)
    .filter(e => e);
  let a = steps.find(step => step.id === match[0]);
  if (!a) {
    a = new Step(match[0]);
    steps.push(a);
  }
  let b = steps.find(step => step.id === match[1]);
  if (!b) {
    b = new Step(match[1]);
    steps.push(b);
  }
  b.dependsOn(a);
  steps.sort((a, b) => a.id.localeCompare(b.id));
  return steps;
}

function executionOrder(steps) {
  const root = steps.find(step => step.canRun());
  if (root) {
    root.run();
    return root.id + executionOrder(steps);
  }
  return "";
}

module.exports = {
  parseLine,
  executionOrder
};

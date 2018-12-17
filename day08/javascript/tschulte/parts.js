"use strict";

function parseLine(line) {
  const numbers = line.split(/ /g).map(Number);
  return new Node(numbers);
}

class Node {
  constructor(numbers) {
    const childCount = numbers.shift();
    const metadataCount = numbers.shift();
    this.children = [];
    for (let i = 0; i < childCount; i++) {
      this.children.push(new Node(numbers));
    }
    this.metadata = numbers.splice(0, metadataCount);
  }

  sum(a, b) {
    return a + b;
  }

  sumOfMetadataDeep() {
    return (
      this.metadata.reduce(this.sum, 0) +
      this.children.map(child => child.sumOfMetadataDeep()).reduce(this.sum, 0)
    );
  }
  value() {
    if (this.children.length === 0) {
      return this.metadata.reduce(this.sum, 0);
    }
    return this.metadata
      .map(i => i - 1)
      .filter(i => i >= 0 && i < this.children.length)
      .map(i => this.children[i].value())
      .reduce(this.sum, 0);
  }
}

module.exports = {
  parseLine
};

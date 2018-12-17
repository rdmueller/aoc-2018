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

  sumOfMetadataDeep() {
    const sum = (sum, value) => sum + value;
    return (
      this.metadata.reduce(sum, 0) +
      this.children.map(child => child.sumOfMetadataDeep()).reduce(sum, 0)
    );
  }
}

module.exports = {
  parseLine
};

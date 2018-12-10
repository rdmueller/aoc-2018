"use strict";

function reduce(polymers) {
  let changed = false;
  const length = polymers.length;
  for (let i = 1; i < length; i++) {
    const c1 = polymers.charAt(i - 1);
    const c2 = polymers.charAt(i);
    if (c1 !== c2 && c1.toLowerCase() === c2.toLowerCase()) {
      polymers = polymers.substring(0, i - 1) + polymers.substring(i + 1);
      changed = true;
    }
  }
  if (changed) return reduce(polymers);
  return polymers;
}

exports.reduce = reduce;

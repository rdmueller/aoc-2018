// tag::histogram[]
exports.histogram = function(input) {
  const letters = {};
  for (i = 0; i < input.length; i++) {
    let count = letters[input.charAt(i)];
    if (!count) {
      count = 0;
    }
    letters[input.charAt(i)] = ++count;
  }
  return letters;
};
// end::histogram[]

// tag::nTimes[]
exports.nTimes = function(n, histogram) {
  for (let key in histogram) {
    if (histogram[key] === n) {
      return 1;
    }
  }
  return 0;
};
// end::nTimes[]

// tag::checksum[]
exports.checksum = function(inputs) {
  return inputs
    .map(input => exports.histogram(input))
    .map(hist => [exports.nTimes(2, hist), exports.nTimes(3, hist)])
    .reduce((prev, curr) => [prev[0] + curr[0], prev[1] + curr[1]], [0, 0])
    .reduce((prev, curr) => prev * curr, 1);
};
// end::checksum[]

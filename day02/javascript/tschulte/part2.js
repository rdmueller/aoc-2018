exports.commonString = function(a, b) {
  const length = a.length < b.length ? a.length : b.length;
  let s = "";
  for (let i = 0; i < length; i++) {
    let ac = a.charAt(i);
    let bc = b.charAt(i);
    if (ac === bc) {
      s += ac;
    }
  }
  return s;
};

exports.correctBoxId = function(input) {
  for (let i = 0; i < input.length - 1; i++) {
    for (let j = i + 1; j < input.length; j++) {
      let common = exports.commonString(input[i], input[j]);
      if (common.length === input[i].length - 1) {
        return common;
      }
    }
  }
  return "";
};

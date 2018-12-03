exports.firstDuplicateFrequency = function(numbers) {
  let frequency = 0;
  const frequencies = [0];

  while (true) {
    for (i = 0; i < numbers.length; i++) {
      frequency += numbers[i];
      if (frequencies.includes(frequency)) {
        return frequency;
      }
      frequencies.push(frequency);
    }
  }
};

"use strict";

/*
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
*/

// tag::splitLine[]
exports.splitLine = function(line) {
  return line.split(/\[\d+-\d+-\d+ \d+:(\d+)\] (.*)/g).filter(result => result);
};
// end::splitLine[]

// tag::guardBeginsShift[]
exports.guardBeginsShift = function(line) {
  const guard = line
    .split(/Guard #(\d+) begins shift/)
    .filter(result => result)
    .map(Number);
  if (guard.length > 0) return guard[0];
  return null;
};
// end::guardBeginsShift[]

// tag::parseLine[]
exports.parseLine = function(alldata, line) {
  const split = exports.splitLine(line);
  const minute = Number(split[0]);
  const guard = exports.guardBeginsShift(split[1]);
  if (guard) {
    let i = alldata.findIndex(data => data.guard === guard);
    let data = null;
    if (i >= 0) {
      data = alldata.splice(i, 1)[0];
    } else {
      data = { guard: guard, timeAsleep: 0, asleep: {} };
    }
    alldata.splice(0, 0, data);
  } else {
    let data = alldata[0];
    if (split[1] === "falls asleep") {
      data.startSleep = minute;
    } else if (split[1] === "wakes up") {
      for (let i = data.startSleep; i < minute; i++) {
        if (data.asleep[i]) {
          data.asleep[i] = data.asleep[i] + 1;
        } else {
          data.asleep[i] = 1;
        }
        data.timeAsleep++;
      }
      data.startSleep = null;
    }
  }
  return alldata;
};
// end::parseLine[]

// tag::guardMostAsleep[]
exports.guardMostAsleep = function(data) {
  data.sort((a, b) => b.timeAsleep - a.timeAsleep);
  return data[0];
};
// end::guardMostAsleep[]

// tag::overlappingSleepTimes[]
exports.overlappingSleepTimes = function(guard) {
  const overlapping = [];
  for (let i in guard.asleep) {
    if (guard.asleep[i] > 1)
      overlapping.push({ minute: Number(i), days: guard.asleep[i] });
  }
  overlapping.sort((a, b) => b.days - a.days);
  return overlapping;
};
// end::overlappingSleepTimes[]

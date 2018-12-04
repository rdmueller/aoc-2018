#!/usr/bin/env python3
import logParser
import datetime
import transformer

parser = logParser.Parser()
assert parser.parse("[1518-11-01 00:00] Guard #10 begins shift") == \
       [datetime.datetime(1518, 11, 1, 0, 0), 'start', 10]
assert parser.parse("[1518-11-01 00:05] falls asleep") == \
       [datetime.datetime(1518, 11, 1, 0, 5), 'asleep', -1]
assert parser.parse("[1518-11-02 00:50] wakes up") == \
       [datetime.datetime(1518, 11, 2, 0, 50), 'wakeup', -1]

log = parser.readLog("testInput.txt")
guards = transformer.transform(log)
assert transformer.countMinutes(guards) == 240
assert transformer.countMinutes2(guards) == 4455

log = parser.readLog("input.txt")
guards = transformer.transform(log)
print(transformer.countMinutes(guards))
print(transformer.countMinutes2(guards))

import re
import datetime as datetime

class Parser:
    # [1518-11-01 00:00] Guard #10 begins shift
    # [1518-11-01 00:05] falls asleep
    # [1518-11-01 00:25] wakes up
    def parse(self, line):
        expression = '^\\[([-0-9: ]+)\\] (Guard #([0-9]+) begins shift|falls asleep|wakes up)'
        entry = re.search(expression, line)
        if (entry.group(2) == "falls asleep"):
            type = "asleep"
        elif (entry.group(2) == "wakes up"):
            type = "wakeup"
        else:
            type = "start"
        if (entry.group(3) is None):
            guardNumber = -1
        else:
            guardNumber = int(entry.group(3))
        return [
            datetime.datetime.strptime(entry.group(1), '%Y-%m-%d %H:%M'),
            type,
            guardNumber
        ]

    def readLog(self, fileName):
        def sortFunc(e):
            return e[0]

        input = open(fileName).readlines()
        log = []
        for line in input:
            log.append(self.parse(line))
        log.sort(key=sortFunc)
        return log

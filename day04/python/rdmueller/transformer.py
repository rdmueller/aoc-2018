

# tag::main[]
def transform(log):
    guards = {}
    currentGuard = -1
    start = 0
    end = 0
    for entry in log:
        if (entry[1] == 'start'):
            if (start != 0):
                for minute in range(start, end-1):
                    guards[currentGuard][minute] += 1
            currentGuard = entry[2]
            if (currentGuard not in guards):
                guards[currentGuard] = []
                for minute in range(0, 59):
                    guards[currentGuard].append(0)
        elif (entry[1] == 'asleep'):
            start = entry[0].minute
        else:
            end = entry[0].minute
            for minute in range(start, end):
                guards[currentGuard][minute] += 1
            start = 0
    return guards
# end::main[]


# tag::starOne[]
def countMinutes(guards):
    maxGuard = -1
    maxSleep = -1
    for guard in guards:
        totalSleep = 0
        for minutes in guards[guard]:
            totalSleep += minutes
        if (totalSleep > maxSleep):
            maxSleep = totalSleep
            maxGuard = guard
    maxMinutes = 0
    maxMinute = -1
    i = 0
    for minutes in guards[maxGuard]:
        if (minutes > maxMinutes):
            maxMinutes = minutes
            maxMinute = i
        i += 1
    return (maxGuard * maxMinute)
# end::starOne[]


# tag::starTwo[]
def countMinutes2(guards):
    maxGuard = -1
    maxSleep = -1
    maxMinute = -1
    for guard in guards:
        i = 0
        for minutes in guards[guard]:
            if (minutes > maxSleep):
                maxSleep = minutes
                maxGuard = guard
                maxMinute = i
            i += 1
    return (maxGuard * maxMinute)
# end::starTwo[]

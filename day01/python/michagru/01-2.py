import sys

aufgabe = list(map(int, sys.stdin))
#print(aufgabe)

def findRepeatedFreq (freq):
    'find repeated frequency in list freq'
    currentFreq = 0
    seenFreq = {}
    while True:
        for line in freq:
            currentFreq += line
            #print (currentFreq)
            if currentFreq in seenFreq:
                #print("Found seen Frequency: ", currentFreq)
                return currentFreq
            else:
                seenFreq[currentFreq] = 1

testlist1 = [3, 3, 4, -2, -4]
assert findRepeatedFreq(testlist1) == 10, "testlist1"

testlist2 = [-6, 3, 8, 5, -6]
assert findRepeatedFreq(testlist2) == 5, "testlist2"

print("Lösung: ", findRepeatedFreq(aufgabe))


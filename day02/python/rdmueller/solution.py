#!/usr/bin/env python3
import sys

# tag::countLetters[]
def countLetters(id):
    if (type(id) is str):
        result = {2: [], 3: []}
        chars = "abcdefghijklmnopqrstuvwxyz"
        for char in chars:
            count = id.count(char)
            if count == 2 or count == 3:
                result[count].append(char)
        return result
    else:
        result = []
        for singleId in id:
            result.append(countLetters(singleId))
        return result
# end::countLetters[]


# tag::createChecksum[]
def createChecksum(letterCount):
    countTwo = 0
    countThree = 0
    for count in letterCount:
        if (len(count[2]) > 0):
            countTwo += 1
        if (len(count[3]) > 0):
            countThree += 1
    return countTwo * countThree
# end::createChecksum[]


testInput = [
    "abcdef", "bababc", "abbcde",
    "abcccd", "aabcdd", "abcdee",
    "ababab"
            ]
assert countLetters(testInput[0]) == {2: [], 3: []}
assert countLetters(testInput[1]) == {2: ["a"], 3: ["b"]}
assert countLetters(testInput[2]) == {2: ["b"], 3: []}
assert countLetters(testInput[3]) == {2: [], 3: ["c"]}
assert countLetters(testInput[4]) == {2: ["a", "d"], 3: []}
assert countLetters(testInput[5]) == {2: ["e"], 3: []}
assert countLetters(testInput[6]) == {2: [], 3: ["a", "b"]}

assert createChecksum(countLetters(testInput)) == 12

file = open('input.txt', 'r')
input = file.readlines()

# tag::solutionOne[]
print("Solution Star One: ", createChecksum(countLetters(input)))
# end::solutionOne[]

testInput = [
    "abcde", "fghij", "klmno",
    "pqrst", "fguij", "axcye",
    "wvxyz",
]


# tag::calcDistance[]
def calcDistance(id1, id2):
    distance = 0
    i = 0
    for character in id1:
        if character != id2[i]:
            distance += 1
        i += 1
    return distance
# end::calcDistance[]


assert calcDistance("abcde", "axcye") == 2
assert calcDistance("fghij", "fguij") == 1

# tag::solutionTwo[]
# calc all distances
i = 1
for id1 in input:
    for id2 in range(i, len(input)-1):
        if calcDistance(id1, input[id2]) == 1:
            print(id1)
            print(input[id2])
    i += 1
# end::solutionTwo[]

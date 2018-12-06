#!/usr/bin/env python3


# tag::starOne[]

def doReaction(input):
    pos = 0
    while True:
        # print(input)
        if (input[pos].lower() == input[pos+1].lower()):
            if (input[pos] != input[pos+1]):
                # print(len(input), pos, input[pos], input[pos+1])
                input = input[0:pos]+input[pos+2:]
                pos -= 2
                if (pos<0):
                    pos = -1
        pos += 1
        if (pos==len(input)-1):
            break
    return len(input)


input = open("input.txt").read()
print("Star One: ", doReaction(input))
# end::starOne[]

# tag::starTwo[]
minLenght = 9390
minChar = ""
for char in "ABCDEFGHIJKLMNOPQRSTUVWXYZ":
    input = open("input.txt").read()
    input = input.replace(char, "")
    input = input.replace(char.lower(), "")
    length = doReaction(input)
    if (length < minLenght):
        minLenght = length
        minChar = char
print(minLenght, minChar)
# end::starTwo[]

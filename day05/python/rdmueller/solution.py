#!/usr/bin/env python3


# tag::starOne[]
input = open("testinput.txt").read()
changed = True
output = ""
skip = False
print(input)
while changed:
    changed = False
    for i in range(0, len(input)-1):
        if (skip == True):
            skip = False
        else:
            if (input[i].lower() == input[i+1].lower()):
                if (input[i] != input[i+1]):
                    skip = True
                    changed = True
            else:
                output += input[i]
    if (not (skip == True)):
        output += input[-1]
    print(output)
    input = output
    output = ""
print(input)
print(len(input))
assert input == "dabCBAcaDA"
# end::starOne[]

# tag::starTwo[]
# end::starTwo[]

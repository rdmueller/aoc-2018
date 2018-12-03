#!/usr/bin/env python3
import re


# tag::Fabric[]
class Fabric:
    fabric = []
    width = 0
    height = 0

    def __init__(self, width, height):
        self.fabric = []
        self.width = width
        self.height = height
        for x in range(width):
            self.fabric.append([])
            for y in range(height):
                self.fabric[x].append([])

    def set(self, x, y, value):
        self.fabric[x][y].append(value)
        return self.fabric[x][y]

    def parseClaim(self, claimString):
        expression = '#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)'
        claim = re.search(expression, claimString)
        return {
            "id":    int(claim.group(1)),
            "x":     int(claim.group(2)), "y":      int(claim.group(3)),
            "width": int(claim.group(4)), "height": int(claim.group(5))
        }

    def setClaim(self, claimString):
        # claim is something like #1 @ 1,3: 4x4
        claim = self.parseClaim(claimString)
        for x in range(claim["x"], claim["x"]+claim["width"]):
            for y in range(claim["y"], claim["y"]+claim["height"]):
                self.set(x, y, claim["id"])

    def overlap(self):
        overlap = 0
        for x in range(self.width):
            for y in range(self.height):
                if (len(self.fabric[x][y]) > 1):
                    overlap += 1
        return overlap

    # tag::solutionTwo[]
    def unique(self):
        for id in range(1, 1373):
            unique = True
            for x in range(self.width):
                for y in range(self.height):
                    if (id in self.fabric[x][y]):
                        if (len(self.fabric[x][y]) > 1):
                            unique = False
            if (unique is True):
                return id
    # end::solutionTwo[]

# end::Fabric[]


fabric = Fabric(11, 9)
expected = {"id": 1, "x": 2, "y": 3, "width": 4, "height": 5}
assert fabric.parseClaim('#1 @ 2,3: 4x5') == expected
testInput = [
    "#1 @ 1,3: 4x4",
    "#2 @ 3,1: 4x4",
    "#3 @ 5,5: 2x2"
]
for claim in testInput:
    fabric.setClaim(claim)
assert fabric.overlap() == 4

file = open('input.txt', 'r')
input = file.readlines()

# tag::solutionOne[]
fabric = Fabric(1000, 1000)
for claim in input:
    fabric.setClaim(claim)

print(fabric.overlap())
# end::solutionOne[]

print(fabric.unique())
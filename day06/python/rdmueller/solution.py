#!/usr/bin/env python3


# tag::starOne[]

def distance(p1, p2):
    return abs(p1[0]-p2[0])+abs(p1[1]-p2[1])


def shortestDistance(p, points):
    distances = []
    for point in points:
        distances.append(distance(point, p))
    minDist = min(distances)
    sumDist = sum(distances)
    pointNums = [i for i, x in enumerate(distances) if x == minDist]
    # print(">> ", pointNums)
    if (len(pointNums) == 1):
        return [minDist, pointNums[0], sumDist]
    else :
        return [minDist, -1, sumDist]


input = open("input.txt").readlines()
points = []
xmin = None
xmax = None
ymin = None
ymax = None
for line in input:
    point = [int(x.strip()) for x in line.split(',')]
    points.append(point)
    if (xmin is None or point[0] < xmin):
        xmin = point[0]
    if (xmax is None or point[0] > xmax):
        xmax = point[0]
    if (ymin is None or point[1] < ymin):
        ymin = point[0]
    if (ymax is None or point[1] > ymax):
        ymax = point[1]

print(points)

area = [0 for p in points]
alph = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
sumbelow = 0
for y in range(ymin-100, ymax+100):
    for x in range(xmin-100, xmax+100):
        sd = shortestDistance([x, y], points)
        if (sd[1] != -1):
            if (x == xmin or x == xmax or y == ymin or y == ymax):
                area[sd[1]] = -1
            else:
                area[sd[1]] += 1
            # print(alph[sd[1]], end="")
        #else:
            # print(".", end="")
        if (sd[2] < 10000):
            sumbelow += 1
    # print()
print(max(area))
print(sumbelow)
# end::starOne[]

# tag::starTwo[]
# end::starTwo[]

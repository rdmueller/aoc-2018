from functools import reduce

coordinates = []
dist_map = {}

with open('input.txt') as input_file:
    for coordinate_str in input_file:
        q, p = coordinate_str.rstrip().split(', ')
        coordinates.append((int(q), int(p)))

## Init matrix

matrix_len_x = max(coordinates, key = lambda c: c[0])[0] + 1
matrix_len_y = max(coordinates, key = lambda c: c[1])[1] + 1
matrix = [[None for y in range(matrix_len_y)] for x in range(matrix_len_x)]

## Calculate distances

def calcManDistance(p, q):
    return abs(p[0] - q[0]) + abs(p[1] - q[1])

def updateInPlace(p, q, matrix):
    current_owner = matrix[q[0]][q[1]]
    dist = calcManDistance(p, q)
    dist_map[q] = dist_map.get(q, [])
    dist_map[q].append(dist)
    if current_owner == None or dist < current_owner[0]:
        matrix[q[0]][q[1]] = (dist, p)
    elif dist == current_owner[0]:
        matrix[q[0]][q[1]] = (dist, None) # There are multiple claims

[[[updateInPlace(coord, (x, y), matrix) for y in range(matrix_len_y)] for x in range(matrix_len_x)] for coord in coordinates]

## Calculate none-infinite areas
areas = {}
def updateArea(x, y, max_x, max_y, matrix, areas):
    p = matrix[x][y][1]
    if p is None: return
    current_p_area = areas.get(p, 0)
    if x == 0 or x == max_x - 1 or y == 0 or y == max_y - 1:
        areas[p] = -1 # Infinity
    elif current_p_area != -1:
        areas[p] = current_p_area + 1

[[updateArea(x, y, matrix_len_x, matrix_len_y, matrix, areas) for x in range(matrix_len_x)] for y in range(matrix_len_y)]
max_area = areas[max(areas, key = lambda k: areas[k])]

print('Solution to part 1: %s' % (max_area,))

limit_total_dist = 10000
region_size = 0
for dist_list in dist_map.values():
    coord_total_dist = reduce((lambda x, y: x + y), dist_list)
    if coord_total_dist < limit_total_dist: region_size += 1

print('Solution to part 2: %i' % (region_size,))

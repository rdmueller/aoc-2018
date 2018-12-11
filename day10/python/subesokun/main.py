INPUT_FILE_NAME = 'input.txt'

def parseSpotInput(text):
    tmp_split = text.split('>')
    pos_str, velo_str = tmp_split[0], tmp_split[1]
    pos = tuple([int(coord) for coord in pos_str.replace(' ', '').split('<')[1].split(',')])
    velo = tuple([int(velo) for velo in velo_str.replace(' ', '').split('<')[1].split(',')])
    return [pos, velo]

def hasSpotAt(x, y, spots):
    for spot in spots:
        if spot[0] == (x,y):
            return True
    return False

def printSpots(spots):
    min_x = min(spots, key = lambda s: s[0][0])[0][0]
    max_x = max(spots, key = lambda s: s[0][0])[0][0]
    min_y = min(spots, key = lambda s: s[0][1])[0][1]
    max_y = max(spots, key = lambda s: s[0][1])[0][1]
    for y in range(min_y, max_y + 1):
        print(''.join(['#' if hasSpotAt(x, y, spots) else ' ' for x in range(min_x, max_x + 1)]))

def tick(spots, dir=1):
    for spot in spots:
        spot[0] = (spot[0][0] + dir * spot[1][0], spot[0][1] + dir * spot[1][1])

def searchAndPrint(spots):
    current_min_delta_y = None
    current_second = 0
    while True:
        tick(spots)
        current_second += 1
        min_y = min(spots, key = lambda s: s[0][1])[0][1]
        max_y = max(spots, key = lambda s: s[0][1])[0][1]
        min_delta_y = max_y - min_y
        if current_min_delta_y == None or min_delta_y < current_min_delta_y:
            current_min_delta_y = min_delta_y
        else:
            tick(spots, -1)
            printSpots(spots)
            return current_second - 1

spots = []
with open(INPUT_FILE_NAME) as input_file:
    for line in input_file:
        spots.append(parseSpotInput(line.rstrip()))

print('Solution to part 1:')

seconds = searchAndPrint(spots)

print('Solution to part 2: %i' % (seconds,))
GRID_SERIAL_NUMBER = 6878
GRID_DIM = 300

def calcCellPowerLevel(x, y, serialNumber):
        return int(str((((x + 10) * y) + serialNumber) * (x + 10))[-3]) - 5

def initCells(serialNumber):
        return [[calcCellPowerLevel(x, y, serialNumber) for y in range(1, GRID_DIM + 1)] for x in range(1, GRID_DIM +1)]

def calcAreaPower(cells, x, y, w, h):
        power = 0
        for pos_x in range(x, x + h):
                for pos_y in range(y, y + w):
                        power += cells[pos_x-1][pos_y-1]
        return power

def findMaxPowerArea(cells, w, h):
        max_power = 0
        max_power_cell = None
        for y in range(1, GRID_DIM - (h - 2)):
                for x in range(1, GRID_DIM - (w - 2)):
                        power = calcAreaPower(cells, x, y, w, h)
                        if power > max_power:
                                max_power = power
                                max_power_cell = (x, y)
        return max_power_cell, max_power

def findDynSquareSizeMaxPowerArea(cells):
        max_power = 0
        max_power_dim = None
        max_power_cell = None
        for dim in range(1, GRID_DIM + 1):
                power_cell, power = findMaxPowerArea(cells, dim, dim)
                if power > max_power:
                        max_power = power
                        max_power_dim = dim
                        max_power_cell = power_cell
                print('> Max power', dim, max_power, max_power_cell)
        return max_power_cell, max_power, max_power_dim

cells = initCells(GRID_SERIAL_NUMBER)

max_power_cell, max_power = findMaxPowerArea(cells, 3, 3)
print('Solution to part 1: %i,%i' % max_power_cell)

# Works but pretty slow as only one CPU gets utilized :o)
max_power_cell, max_power, max_power_dim = findDynSquareSizeMaxPowerArea(cells)
print('Solution to part 2: %i,%i,%i' % (max_power_cell[0], max_power_cell[1], max_power_dim))

const fs = require('fs')
const FILE = 'input.txt'

function getInput (filepath) {
  const input = fs.readFileSync(FILE, 'utf8')
  return input.split('\n')
    .filter(e => e.length)
    .map((e, i) => ({ id: i, x: parseInt(e.split(', ')[0]), y: parseInt(e.split(', ')[1]) }))
}

function part1 () {
  const coords = getInput(FILE)
  const xMax = 1 + coords.reduce((p, c) => {
    if (c.x > p) p = c.x
    return p
  }, 0)
  const yMax = 1 + coords.reduce((p, c) => {
    if (c.y > p) p = c.y
    return p
  }, 0)
  const row = new Array(xMax).fill(0).map((e, i) => i)
  const col = new Array(yMax).fill(0).map((e, i) => i)

  // generate list of all coord IDs that touch any of the outer borders
  const borderIds = row.map(e => ({ x: e, y: 0 }))
    .concat(row.map(e => ({ x: e, y: yMax })))
    .concat(col.map(e => ({ x: 0, y: e })))
    .concat(col.map(e => ({ x: xMax, y: e })))
    .map(e => {
      const distances = coords.map(coord => manhatten(coord.x, coord.y, e.x, e.y))
      const ix = distances.indexOf(Math.min(...distances))
      const closestNeighbor = coords[ix]
      return closestNeighbor
    })
    .reduce((p, closestNeighbor) => {
      if (!p.includes(closestNeighbor.id)) p.push(closestNeighbor.id)
      return p.sort()
    }, [])

  const grid = createGrid(xMax, yMax, coords)
  const countMap = coords
    .filter(e => !borderIds.includes(e.id))
    .map(e => {
      return {
        id: e.id,
        count: grid
          .map(row => row.filter(id => id === e.id).length)
          .reduce((p, c) => p + c, 0)
      }
    })
  const largestArea = countMap[countMap.map(e => e.count).indexOf(Math.max(...countMap.map(e => e.count)))]
  console.log('Solution part 1, largest area: ', largestArea)
}

// create a grid and fill it with coord IDs according to manhatten distance
function createGrid (width, height, coords) {
  const col = new Array(height).fill(0).map((e, i) => i)
  const grid = col
    .map(e => new Array(width).fill(-1))
    .map((ey, y) => ey.map((ex, x) => {
      const distances = coords.map(coord => manhatten(coord.x, coord.y, x, y))
      // return -1 if multiple "shortest" distances
      const ix = distances.indexOf(Math.min(...distances))
      distances.sort((a, b) => a - b)
      if (distances[0] === distances[1]) {
        return -1
      }
      const closestNeighbor = coords[ix]
      return closestNeighbor.id
    }))

  return grid
}
// calculate the space occupied by a given coordinate ID

// manhatten distance for two points
function manhatten (x1, y1, x2, y2) {
  return Math.abs(x1 - x2) + Math.abs(y1 - y2)
}
part1()

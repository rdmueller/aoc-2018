#create the map with * for each inch
def createFabricMap(widthInches,heightInches):
    fabricMap=[['*' for x in range(widthInches)] for y in range(heightInches)]
    return fabricMap

#renders the fabric map
def printFabricMap(fabricMap):
    for x in fabricMap:
        for y in x:
            print(y,end="") # one row
        print() # new Line
    return

def setClaim(line,fabricMap):
    #1 @ 56,249: 24x16 example claim

    line=line.split() # split the line by spaces
    line[2]=line[2].replace(":","") # get rid of the comma

    distanceLeft=line[2].split(',')[0] # left distance left from commy
    distanceTop=line[2].split(',')[1] # top distance right from comma
    claimWidth=line[3].split('x')[0] # claim width left from x
    claimHeight=line[3].split('x')[1] # claim height right from x

    # set claims
    for i in range(0,int(claimWidth)):
        for j in range(0,int(claimHeight)):
            # set the square inch to 1 if not claim set yet
            if fabricMap[int(distanceTop)+j][int(distanceLeft)+i]=='*':
                fabricMap[int(distanceTop)+j][int(distanceLeft)+i]=1
            # set the square inch to x if a claim was already made to this square inch
            elif fabricMap[int(distanceTop)+j][int(distanceLeft)+i]==1:
                fabricMap[int(distanceTop)+j][int(distanceLeft)+i]='x'
    return fabricMap

def countOverlaps(fabricMap):
    return (sum(x.count('x') for x in fabricMap))

# create the 2-dimensional list representing the fabric map
fabricMap=createFabricMap(widthInches = 1000,heightInches = 1000)

with open("puzzleInput") as file: #openFile
    for line in file: #walk through each line
        fabricMap=setClaim(line,fabricMap)

print ("The fabric has {} square inches of overlap".format(countOverlaps(fabricMap)))
#printFabricMap(fabricMap)
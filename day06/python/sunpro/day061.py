import numpy as np

def parsorCoord(coordinate):
    co = coordinate.split(', ')
    return np.array([int(co[0]),int(co[1])],dtype=np.int16)

def drawArea(bottom,right):
    area = np.zeros((bottom+1,right+1,2))
    for i in range(bottom+1):
        area[i,:,0] = i
    for j in range(right+1):
        area[:,j,1] = j
    return area

f = open('input.txt','r')
ins = f.readlines()

# parse the input
arrCoord = np.array(list(map(parsorCoord,ins)),dtype=np.int16)

# find the bottom and right of the area

right = arrCoord[:,0].max()

bottom = arrCoord[:,1].max()

# calculate the distence to every coordinate
listDists =[]
for coord in arrCoord:
    area = drawArea(bottom,right)
    dist = abs(area - coord).sum(axis = 2)
    listDists.append(dist)
	
listDists = np.array(listDists)

## day061
listdist = []
for i in range(bottom+1):
    for j in range(right+1):
        num = np.where(listDists[:,i,j]==listDists[:,i,j].min())[0]
        if num.shape[0]>1:
            listdist.append(-1)
        else:
            listdist.append(num[0])
listnums = np.array(listdist).reshape((bottom+1,right+1))
# delete the infinite coordinates
listINF = np.array([])
listINF = np.append(listINF,listnums[0])
listINF = np.append(listINF,listnums[-1])
listINF = np.append(listINF,listnums[:,0])
listINF = np.append(listINF,listnums[:,-1])
setINF = set(listINF)
# get the largest one
arrCount = {}
for c in range(arrCoord.shape[0]):
    if c not in setINF:
        arrCount[str(c)] = np.where(listnums==c)[0].shape[0]
sortedCount = sorted(arrCount.items(),key = lambda x:x[1],reverse=True)
print(sortedCount[0][1])

## day062
print(np.where(listDists.sum(axis=0)<10000)[0].shape[0])





















import re
import numpy as np

def findlen(ins):
    ind = np.array(list(map(ord,ins)),dtype = np.int16)
    flag = -1
    count = 0
#     print('ok')
    while(flag == -1):
        ine = np.zeros_like(ind)
        ine[0:-1] = ind[1:]
        diff = ind - ine
        flag = 1
        i = 0
        r =np.array([]) 
        same = np.where(abs(diff) == 32)[0]
#         print(same)
        for i in same:
            if  i not in r:
                r = np.append(r,np.array([i,i+1]))
#         print(r)
#         print('OK')
        if r.shape[0] > 0:
            flag = -1
            ind = np.delete(ind,r)
        count+=1
#         print('循环:',count)
    return ind.shape[0]
	
def suba(cc):
    return re.sub(re.compile(cc),'',ins)

f = open('input.txt','r')
ins = f.read()

print(findlen(ins))

print(min(list(map(findlen,map(suba,['[' + chr(n)+chr(n+32) + ']' for n in range(65,65+26)])))))





















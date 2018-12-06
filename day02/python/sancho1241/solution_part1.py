# init variables
checksum=0
countTwice=0
countThrice=0

with open("puzzleInput") as file: #openFile
    for line in file: #walk through each line

        # init "already counted in this row" flags for each new line
        doublesCounted=False
        triplesCounted=False

        #print (line)
        #print("string length: {}".format(len(line)))
        while len(line) > 0:
            #print("character to be searched: {} which was found  {} times ".format(line[0],line.count(line[0])))

            #count doubles only if not counted in this row yet
            if ((line.count(line[0]) == 2) and not doublesCounted):
                countTwice += 1
                #print("2 counter value: {} ".format(countTwice))
                doublesCounted=True # set counted flag for doubles

            #count triples only if not counted in this row yet
            if ((line.count(line[0]) == 3) and not triplesCounted):
                countThrice += 1
                #print("3 counter value: {} ".format(countThrice))
                triplesCounted=True # set counted flag for triples

            #remove all additional occurences of the character from line
            line=line.replace(line[0],"")

            #line=line[1:]
            #print("the new line is:{} ".format(line))
        #print(line.count(line[1]))
    checksum = checksum + countTwice * countThrice
    print("The checksum is: {} ".format(checksum))
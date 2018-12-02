sum=0 #initialize sum
with open("puzzleinput") as file: #openFile
    for line in file: #walk through each line
        sum = sum + int(line) # add int converted string to sum
    print (sum) # print sum once end of lines is reached
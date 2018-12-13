#!/usr/bin/env groovy

// tag::helper[]
Map readInput(fileName) {
    Map out = [max:[x:0,y:0], carts:[:]]
    Map underlying = ['>':'-','<':'-','^':'|','v':'|',]
    Map nextDir = ['>':'^','<':'v','^':'<','v':'>',]
    def lines = new File(fileName).text.split("\n")
    lines.eachWithIndex { line, y ->
        line.eachWithIndex { c, x ->
            if (c in ['<', '>', '^', 'v']) {
                out.carts["$x|$y"] = [underlying: underlying[c], nextDirection: nextDir[c], memory: 0]
            }
            out["$x|$y"] = c
            if (x>out.max.x) {out.max.x=x}
            if (y>out.max.y) {out.max.y=y}
        }
    }
    return out
}
// end::helper[]

void printTracks(tracks) {
    //def f = new File("test.txt")
    //f.write("")
    for (int y=0; y<tracks.max.y+1; y++)  {
        def line = ""
        for (int x=0; x<tracks.max.x+1; x++)  {
            def c = tracks["$x|$y"]?:" "
            line += c
            //print (tracks["$x|$y"]?:" ")
        }
        println line
        //f.append(line+"\n")
    }
}
// tag::nextState[]
Map calcNextState(tracks) {
    def newTracks = [max:[x:tracks.max.x,y:tracks.max.y], carts:[:]]
    def numCarts = 0
    for (int y=0; y<tracks.max.y+1; y++)  {
        for (int x=0; x<tracks.max.x+1; x++)  {
            def c = tracks["$x|$y"]?:" "

            if (c in ['<', '>', '^', 'v', '|','-','\\','/','+',' ', "\r", "\n"]) {
            } else {
                println "--------------- "+c
                sdf
            }
            if (c in ['<', '>', '^', 'v']) {
                numCarts++
                switch (c) {
                    case '>':
                        def next = tracks["${x+1}|$y"]?:" "
                        def next2 = newTracks["${x+1}|${y}"]?:" "
                        if (next2 in ['<', '>', '^', 'v']) {
                            throw new Exception("crash! ${x+1},${y}")
                        }
                        newTracks["${x}|$y"]=tracks.carts["${x}|$y"].underlying
                        newTracks.carts["${x+1}|${y}"]=[underlying:next,memory:tracks.carts["${x}|$y"].memory]
                        switch (next) {
                            case '-':
                                newTracks["${x+1}|$y"] = '>'
                                break;
                            case '\\':
                                newTracks["${x+1}|$y"] = 'v'
                                break;
                            case '/':
                                newTracks["${x+1}|$y"] = '^'
                                break;
                            case '+':
                                memory = tracks.carts["${x}|$y"].memory
                                nextDirection = ['^','>','v'][memory%3]
                                newTracks.carts["${x+1}|${y}"].memory = memory+1
                                newTracks["${x+1}|${y}"] = nextDirection
                                // println "1: "+nextDirection+ " - "+memory
                                break;
                            default:
                                println "error 1: "+next
                                println "${x}, ${y}"
                        }
                        break;
                    case 'v':
                        def next = tracks["${x}|${y+1}"]?:" "
                        def next2 = newTracks["${x}|${y+1}"]?:" "
                        if (next2 in ['<', '>', '^', 'v']) {
                            throw new Exception("crash! ${x},${y+1}")
                        }
                        newTracks["${x}|$y"]=tracks.carts["${x}|$y"].underlying
                        newTracks.carts["${x}|${y+1}"]=[underlying:next,memory:tracks.carts["${x}|$y"].memory]
                        switch (next) {
                            case '|':
                                newTracks["${x}|${y+1}"] = 'v'
                                break;
                            case '/':
                                newTracks["${x}|${y+1}"] = '<'
                                break;
                            case '\\':
                                newTracks["${x}|${y+1}"] = '>'
                                break;
                            case '+':
                                memory = tracks.carts["${x}|$y"].memory
                                nextDirection = ['>','v','<'][memory%3]
                                newTracks.carts["${x}|${y+1}"].memory = memory+1
                                newTracks["${x}|${y+1}"] = nextDirection
                                // println "2: "+nextDirection+ " - "+memory
                                break;
                            default:
                                println "error 2: "+next
                                println "${x}, ${y}"
                        }
                        break;
                    case '^':
                        def next = tracks["${x}|${y-1}"]?:" "
                        def next2 = newTracks["${x}|${y-1}"]?:" "
                        if (next2 in ['<', '>', '^', 'v']) {
                            throw new Exception("crash! ${x},${y-1}")
                        }
                        newTracks["${x}|$y"]=tracks.carts["${x}|$y"].underlying
                        newTracks.carts["${x}|${y-1}"]=[underlying:next,memory:tracks.carts["${x}|$y"].memory]
                        switch (next) {
                            case '|':
                                newTracks["${x}|${y-1}"] = '^'
                                break;
                            case '/':
                                newTracks["${x}|${y-1}"] = '>'
                                break;
                            case '\\':
                                newTracks["${x}|${y-1}"] = '<'
                                break;
                            case '+':
                                memory = tracks.carts["${x}|$y"].memory
                                nextDirection = ['<','^','>'][memory%3]
                                newTracks.carts["${x}|${y-1}"].memory = memory+1
                                newTracks["${x}|${y-1}"] = nextDirection
                                // println "3: "+nextDirection+ " - "+memory
                                break;
                            default:
                                println "error 3"
                                println "${x}, ${y}"
                        }
                        break;
                    case '<':
                        def next = tracks["${x-1}|${y}"]?:" "
                        def next2 = newTracks["${x-1}|${y}"]?:" "
                        if (next2 in ['<', '>', '^', 'v']) {
                            throw new Exception("crash! ${x-1},${y}")
                        }
                        newTracks["${x}|$y"]=tracks.carts["${x}|$y"].underlying
                        newTracks.carts["${x-1}|${y}"]=[underlying:next,memory:tracks.carts["${x}|$y"].memory]
                        switch (next) {
                            case '-':
                                newTracks["${x-1}|${y}"] = '<'
                                break;
                            case '/':
                                newTracks["${x-1}|${y}"] = 'v'
                                break;
                            case '\\':
                                newTracks["${x-1}|${y}"] = '^'
                                break;
                            case '+':
                                memory = tracks.carts["${x}|$y"].memory
                                nextDirection = ['v','^','>'][memory%3]
                                newTracks.carts["${x-1}|${y}"].memory = memory+1
                                newTracks["${x-1}|${y}"] = nextDirection
                                // println "4: "+nextDirection+ " - "+memory
                                break;
                            default:

                                println "error 4 "+next+" / "+c
                                println "${x}, ${y}"
                        }
                        break;
                    default:
                        println "Error: "+c
                }
            } else {
                if (newTracks["$x|$y"]==null) {
                    newTracks["$x|$y"] = c
                }
            }
            //print (tracks["$x|$y"]?:" ")
        }
    }
    return [track:newTracks,carts:numCarts]
}
// end::nextState[]

// tag::starOne[]
def tracks = readInput("testInput.txt")
1700000.times {
    def numCarts
    def res = calcNextState(tracks)
    tracks = res.track
    if (it%1==0) {
        println "\u001b[2J"
        print "\u001b[H"
        println it+": "+res.carts
        //
        printTracks(tracks)
        sleep(1000)
    }
}
println ""
// end::starOne[]

// tag::starTwo[]
// end::starTwo[]
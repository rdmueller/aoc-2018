#!./groovy/groovy-2.5.4/bin/groovy

def src = new File('../.')

def days = []
def languages = []
def coders = [:]
src.eachFile { day ->
    if (day.name.startsWith('day')) {
        day.eachFile { language ->
            if (!language.isFile()) {
                language.eachFile { coder ->
                    if (!coder.isFile()) {
                        if (!coders[coder.name]) {
                            coders[coder.name]=[]
                        }
                        coders[coder.name] << ([day.name,language.name])
                    }
                }
            }
        }
    }
}

def linksToCoders = new File("generated/linksToCoders.adoc")
linksToCoders.write("")
coders.sort().each { coder, data ->
    // * by link:anoff/adventOfCode.html[Andreas Offenhaeuser]
    linksToCoders.append("* by link:${coder}/adventOfCode.html[{${coder}}]\n")
    def daysFile = new File("${coder}/generatedDays.adoc")
    new File("${coder}").mkdir()
    daysFile.write("")
    data.sort{it[0]}.each { datum ->
        //=== Day 1
        //
        //include::../../day01/python/rdmueller/README.adoc[leveloffset=+2]
        daysFile.append("=== Day ${datum[0]-"day"}: ${datum[1]}\n\n")
        daysFile.append("include::../../${datum[0]}/${datum[1]}/${coder}/README.adoc[leveloffset=+2]\n\n")
    }
}
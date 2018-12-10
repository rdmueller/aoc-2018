#!/usr/bin/env groovy

// tag::helper[]
List readInput(fileName) {
    new File(fileName).text.split(" ").collect{it as Integer}
}
// end::helper[]

// tag::starOne[]
List stream
List meta  = []
List nodes = []

def decodeNodes(Integer streamPos, stream, meta, nodes) {
    def numNodes = stream[streamPos++]
    def numMeta  = stream[streamPos++]
    def node = [nodes: [], numNodes: numNodes, numMeta: numMeta, meta: [], value: 0]

    numNodes.times {
        println streamPos
        def newNode
        (newNode, streamPos) = decodeNodes(streamPos, stream, meta, nodes)
        node.nodes << newNode
    }
    numMeta.times {
        metaItem = stream[streamPos++]
        meta << metaItem
        node.meta << metaItem
    }
    println ">>"+ streamPos
    if (numNodes==0) {
        node.value = node.meta.sum()
    } else {
        node.meta.each { index ->
            println "> "+index
            println node
            node.value += node.nodes[index-1]?.value?:0
        }
    }
    return [node, streamPos]
}

stream = readInput("input.txt")

def mainNode = decodeNodes(0, stream, meta, nodes)
println meta
println meta.sum()
println mainNode.value
// end::starOne[]



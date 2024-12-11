package org.example.day10.b

import java.io.File

fun main() {
    val input = File("src/main/resources/day10a.txt").readLines()

    val map = TopographicMap.parse(input)

    println(map.findNumberOfTrails())
}


class TopographicMap(private val maxX: Int, private val maxY: Int, private val grid: List<List<Int>>, private val trailHeads: List<TrailHead>) {

    private fun isInBounds(c: Coordinates): Boolean {
        return c.x in 0..maxX && c.y in 0..maxY
    }

    private fun isInBounds(x: Int, y: Int): Boolean {
        return x in 0..maxX && y in 0..maxY
    }

    fun findNumberOfTrails(): Int {
       return trailHeads.sumOf { t -> findPossibleTrailEnds(t) }
    }

    fun findPossibleTrailEnds(th: TrailHead): Int {
//        println("start search for trailhead  (${th.coordinates.x}, ${th.coordinates.y})")
        val trailEnds = findSubTrails(0, th.coordinates)
        println("found $trailEnds for trailhead (${th.coordinates.x}, ${th.coordinates.y})")
        return trailEnds
    }

    fun findSubTrails(currentValue: Int, currentPosition: Coordinates): Int {
//        println("sub value: $currentValue - (${currentPosition.x}, ${currentPosition.y})")
        if(currentValue == 9) {
//            println("found subTrail" )
            return 1
        }
        val nextSteps = findNeighborPositionsWithValue(currentValue + 1, currentPosition)
        if (nextSteps.isEmpty()) {
//            println("no SubtrailFoudn")
            return 0
        }
        return nextSteps.sumOf { nextStep -> findSubTrails(currentValue + 1, nextStep) }

    }

    private fun findNeighborPositionsWithValue(i: Int, cp: Coordinates): List<Coordinates> {
        val result = mutableListOf<Coordinates>()
        if(isInBounds(cp.x, cp.y-1) && grid[cp.y-1][cp.x] == i) {
            result.add(Coordinates(cp.x, cp.y-1))
        }
        if(isInBounds(cp.x, cp.y+1) && grid[cp.y+1][cp.x] == i) {
            result.add(Coordinates(cp.x, cp.y+1))
        }
        if(isInBounds(cp.x-1, cp.y) && grid[cp.y][cp.x-1] == i) {
            result.add(Coordinates(cp.x-1, cp.y))
        }
        if(isInBounds(cp.x+1, cp.y) && grid[cp.y][cp.x+1] == i) {
            result.add(Coordinates(cp.x+1, cp.y))
        }
        return result
    }


    companion object {
        fun parse(input: List<String>): TopographicMap {
            val grid = mutableListOf<List<Int>>()
            val maxY = input.count() - 1
            val maxX = input[0].count() - 1

            val trailHeads = mutableListOf<TrailHead>()

            input.forEachIndexed { y, line ->
                run {
                    val row = line.toCharArray()
                    row.forEachIndexed { x, c ->
                        run {
                            if (c == '0') {
                                trailHeads.add(TrailHead(Coordinates(x,y)))
                            }
                        }
                    }
                    grid.add(row.map { c -> c.digitToInt() } )
                }
            }

            return TopographicMap(maxX, maxY, grid, trailHeads)
        }
    }
}

class TrailHead(val coordinates: Coordinates) {
}


data class Coordinates(val x: Int, val y: Int)
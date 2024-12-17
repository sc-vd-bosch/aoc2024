package org.example.day14.a

import java.io.File

fun main() {
    val input = File("src/main/resources/day14a.txt").readLines()

    val map = Map.parse(input)

//    map.print()

    for (i in 1..100) {
        map.moveRobots()
    }

//    map.print()

    println(map.safetyFactor())

}

class Robot(var position: Coordinates, val velocity: Velocity) {
    companion object {
        fun parse(input: String): Robot {
            val x = input.split(" ")
            val positionArray = x[0].drop(2).split(",").map { s -> s.toInt() }
            val velocityArray = x[1].drop(2).split(",").map { s -> s.toInt() }
            return Robot(Coordinates(positionArray[0], positionArray[1]), Velocity(velocityArray[0], velocityArray[1]))
        }
    }
}

class Map(val grid: Array<Array<Position>>, val robots: MutableList<Robot>) {


    fun moveRobots() {
        robots.forEach { r-> run {
            removeRobot(r.position)
            r.position = nextCoordinates(r.position, r.velocity)
            placeRobot(r.position)
        }
        }
    }

    fun nextCoordinates(c: Coordinates, v: Velocity): Coordinates {
        val maxY = grid.count()
        val maxX = grid[0].count()

//        println("${c.y + v.down} % $maxY, ${(c.y + v.down) % maxY}")

        val newX = (c.x + v.right).mod(maxX)
        val newY = (c.y + v.down).mod(maxY)

//        println("move (${c.x},${c.y}) with velocity (${v.right}, ${v.down}) to ($newX, $newY)")

        return Coordinates(newX, newY)
    }

    fun removeRobot(c: Coordinates) {
        grid[c.y][c.x].numGuards--
    }

    fun placeRobot(c: Coordinates) {
        grid[c.y][c.x].numGuards++
    }

    fun safetyFactor(): Int {
        val numRows = (grid.count()-1)/2
        val numColumns = (grid[0].count()-1)/2

        val maxY = grid.count()
        val maxX = grid[0].count()

//        println("${maxX - numColumns..maxX}")

        val countInTopLeft = findNumberOfRobotsInSubGrid(0..<numRows, 0..<numColumns)
        val countInTopRight = findNumberOfRobotsInSubGrid(0..<numRows, maxX - numColumns..<maxX)
        val countInBottomLeft = findNumberOfRobotsInSubGrid(maxY-numRows..<maxY, 0..<numColumns)
        val countInBottomRight = findNumberOfRobotsInSubGrid(maxY-numRows..<maxY, maxX - numColumns..<maxX)

//        println("countInTopLeft $countInTopLeft")
//        println("countInTopRight $countInTopRight")
//        println("countInBottomLeft $countInBottomLeft")
//        println("countInBottomRight $countInBottomRight")

        return countInTopLeft * countInTopRight * countInBottomLeft * countInBottomRight

    }

    fun findNumberOfRobotsInSubGrid(yRange: IntRange, xRange: IntRange): Int {
        var result = 0
        for(y in yRange) {
            for(x in xRange) {
                result += grid[y][x].numGuards
            }
        }
        return result
    }

    fun print() {
        println()
        for(y in grid.indices) {
            for(x in grid[0].indices) {
                if(grid[y][x].numGuards == 0) print(".") else print(grid[y][x].numGuards)
            }
            println()
        }
        println()
    }

    companion object {
        fun parse(input: List<String>): Map {
            val robots = mutableListOf<Robot>()
            input.forEach { s -> robots.add(Robot.parse(s)) }

            val maxY = robots.maxOf { r -> r.position.y } + 1
            val maxX = robots.maxOf { r -> r.position.x } + 1

            val grid = Array(maxY) { Array(maxX) { Position()} }

            robots.forEach { r -> grid[r.position.y][r.position.x].numGuards++ }

            return Map(grid, robots)
        }
    }
}

data class Velocity(val right: Int, val down: Int)

data class Coordinates(val x: Int, val y: Int)

class Position {
    var numGuards = 0
}



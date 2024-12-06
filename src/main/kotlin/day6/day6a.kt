package org.example.day6

import java.io.File
import kotlin.math.max

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = File("src/main/resources/day6a.txt").readLines()

    var guard = Position(Int.MAX_VALUE, Int.MAX_VALUE)
    var maxX = 0
    val maxY = input.count()-1

    val grid = Array(input.count()) { "".toCharArray() }
    input.forEachIndexed{ index, line -> run {
            val row = line.toCharArray()
            grid[index] = row
            maxX = max(maxX, row.count()-1)
            val initialGuard = row.indexOf('^')
            if (initialGuard != -1) guard = Position(initialGuard, index)
        }
    }

    val labMap = LabMap(grid, maxX, maxY)

    if (guard.x == Int.MAX_VALUE) {
        println("No initial position found")
        return
    }

    while(labMap.isInBounds(guard.x, guard.y)) {
        val next = guard.nextPosition(labMap)
        val nextInBound = labMap.isInBounds(next.x, next.y)
        if (nextInBound && next.isObstacle(labMap)) {
            guard.turn(labMap)
        } else {
            if(nextInBound) labMap.setValue(next.x, next.y, labMap.value(guard.x, guard.y))
            labMap.setValue(guard.x, guard.y, 'X')
            guard = Position(next.x, next.y)
        }
    }

    println(labMap.findDistinctPatrolSpots())
}


class LabMap(val grid: Array<CharArray>, val maxX: Int, val maxY: Int){
    fun isInBounds(x: Int, y: Int): Boolean {
        return x <= maxX && y <= maxY
    }

    fun value(x: Int, y: Int): Char {
        return grid[y][x]
    }

    fun setValue(x: Int, y: Int, value: Char) {
        grid[y][x] = value
    }

    fun print() {
        for( i in 0 until maxY+1) {
            for( j in 0 until maxX+1) {
                print(grid[i][j])
            }
            println()
        }
    }

    fun findDistinctPatrolSpots(): Int {
        var result = 0
        for( i in 0 until maxY+1) {
            for( j in 0 until maxX+1) {
                if (grid[i][j] == 'X') result++
            }
        }
        return result
    }
}

class Position(val x: Int, val y: Int) {

    fun nextPosition(map: LabMap): Position{
        when(map.value(x,y)) {
            '^' -> return Position(x, y-1)
            '>' -> return Position(x+1, y)
            'v' -> return Position(x, y+1)
            '<' -> return Position(x-1, y)
        }
        return Position(Int.MAX_VALUE, Int.MAX_VALUE)
    }

    fun turn(map: LabMap) {
        when (map.value(x,y)) {
            '^' -> map.setValue(x,y,'>')
            '>' -> map.setValue(x,y,'v')
            'v' -> map.setValue(x,y,'<')
            '<' -> map.setValue(x,y,'^')
        }
    }

    fun isObstacle(map:LabMap): Boolean {
        return map.value(x,y) == '#'
    }
}
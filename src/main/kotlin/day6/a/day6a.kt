package org.example.day6.a

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
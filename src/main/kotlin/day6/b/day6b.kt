package org.example.day6.b

import java.io.File
import kotlin.math.max

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = File("src/main/resources/day6a-example.txt").readLines()

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
    if (guard.x == Int.MAX_VALUE) {
        println("No initial position found")
        return
    }

    val originalLabMap = LabMap(grid, maxX, maxY, guard)

    val gpc = originalLabMap.findGuardPathCoordinates()

    var output = 0

//        val possibleMap = createLabMap(input)
//            possibleMap.placeObstacle(82, 47)
//            if(possibleMap.guardWillLoop())  {
//                output++
//                println("loop found")
//            } else {
//                println("no loop found")
//            }

    gpc.forEach { p -> run {
        println("start possible search ${p.x}, ${p.y}")
        val possibleMap = createLabMap(input)
        if(! (possibleMap.guard.x == p.x && possibleMap.guard.y == p.y)) {
            possibleMap.placeObstacle(p.x, p.y)
            if(possibleMap.guardWillLoop())  {
                output++
                println("loop found")
            } else {
                println("no loop found")
            }
        }
    }
    }


    println(output)
}

fun createLabMap(input: List<String>): LabMap {
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

    val labMap = LabMap(grid, maxX, maxY, guard)

    return labMap

}

class LabMap(private val grid: Array<CharArray>,
             private val maxX: Int,
             private val maxY: Int,
             var guard: Position
){
    private fun isInBounds(x: Int, y: Int): Boolean {
        return x in 0..maxX && y in 0..maxY
    }

    private fun isInBounds(p: Position): Boolean {
        return isInBounds(p.x, p.y)
    }

    private fun value(p: Position): Char {
        return value(p.x, p.y)
    }

    fun value(x: Int, y: Int): Char {
        return grid[y][x]
    }

    fun setValue(p: Position, value: Char) {
        setValue(p.x, p.y, value)
    }

    fun setValue(x: Int, y: Int, value: Char) {
        grid[y][x] = value
    }

    fun placeObstacle(x: Int, y: Int) {
        setValue(x, y, 'O')
    }

    fun print() {
        println()
        for( i in 0 until maxY+1) {
            for( j in 0 until maxX+1) {
                print(grid[i][j])
            }
            println()
        }
        println()
    }

    fun guardWillLoop(): Boolean {
        var oldGuardValue = '.'
        while(isInBounds(guard)) {
//            print()
            val next = guard.nextPosition(this)
            val nextInBound = isInBounds(next)
            if(nextInBound && isInLoop(guard, next)) return true
            if (nextInBound && next.isObstacle(this)) {
                guard.turn(this)
            } else {
                if(nextInBound) {
                    oldGuardValue = value(next)
                    setValue(next, value(guard))
                }
                val newValue = newValue(value(guard),oldGuardValue)
                setValue(guard, newValue)
                guard = Position(next.x, next.y)
            }
        }
        return false
    }

    private fun newValue(guard: Char, oldGuard: Char): Char {
        return when(guard) {
            '^' -> if(oldGuard == 'D') '|' else if(oldGuard == 'R' || oldGuard == 'L'  || oldGuard == '-'  ) '+'  else 'U'
            '>' -> if(oldGuard == 'L') '|' else if(oldGuard == 'U' || oldGuard == 'D'  || oldGuard == '|'  ) '+'  else 'R'
            'v' -> if(oldGuard == 'U') '|' else if(oldGuard == 'R' || oldGuard == 'L'  || oldGuard == '-'  ) '+'  else 'D'
            '<' -> if(oldGuard == 'R') '|' else if(oldGuard == 'U' || oldGuard == 'D'  || oldGuard == '|'  ) '+'  else 'L'
            else -> 'X'
        }
    }


    private fun isInLoop(guard: Position, next: Position): Boolean {
//        println("isinloop: guard(${guard.x}, ${guard.y}, ${value(guard)}) and next(${next.x}, ${next.y}, ${value(next)})")
        return when (value(guard)) {
            '^' -> value(next) == 'U' || value(next) == '|' || value(next) == '+'
            '>' -> value(next) == 'R' || value(next) == '-' || value(next) == '+'
            'v' -> value(next) == 'D' || value(next) == '|' || value(next) == '+'
            '<' -> value(next) == 'L' || value(next) == '-' || value(next) == '+'
            else -> false
        }
    }

    fun findGuardPathCoordinates(): Set<Position> {
        val result = mutableSetOf<Position>()
        while(isInBounds(guard)) {
            val next = guard.nextPosition(this)
            val nextInBound = isInBounds(next)
            if (nextInBound && next.isObstacle(this)) {
                guard.turn(this)
            } else {
                if(nextInBound) setValue(next.x, next.y, value(guard))
                setValue(guard, 'X')
                result.add(Position(guard.x, guard.y))
                guard = Position(next.x, next.y)
            }
        }
        return result
    }
}

class Position(val x: Int, val y: Int) {

    fun nextPosition(map: LabMap): Position {
        when(map.value(x,y)) {
            '^' -> return Position(x, y - 1)
            '>' -> return Position(x + 1, y)
            'v' -> return Position(x, y + 1)
            '<' -> return Position(x - 1, y)
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

    fun isObstacle(map: LabMap): Boolean {
        return map.value(x,y) == '#' || map.value(x,y) == 'O'
    }

}




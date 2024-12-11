package org.example.day6.a

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

    fun placeObstacle(x: Int, y: Int) {
        setValue(x, y, '#')
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
        return map.value(x,y) == '#'
    }

}
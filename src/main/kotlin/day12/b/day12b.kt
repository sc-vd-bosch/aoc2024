package org.example.day12.b

import java.io.File

fun main() {
    val input = File("src/main/resources/day12a.txt").readLines()


    val garden = parseInputToGarden(input)

    garden.findRegions()
    val priceOfFences = garden.regions.sumOf { r -> r.area() * r.numberOfSides(garden) }
    println(priceOfFences)

    garden.print()
}

fun parseInputToGarden(input: List<String>): Garden {
    val grid = mutableListOf<List<Plot>>()
    input.forEachIndexed{ y, line -> run {
        val row = line.toCharArray().mapIndexed { x, c -> Plot(Coordinates(x, y), c) }
        grid.add(row)
    }
    }
    return Garden(grid)
}

class Garden(val grid: List<List<Plot>>) {
    val regions = mutableListOf<Region>()

    fun print() {
        for(y in grid.indices) {
            for(x in grid[0].indices) {
                val plot = grid[y][x]
                print(" ${plot.value} ${plot.numCorners()} ${plot.numOuterCorners(this)}  - ")
            }
            println()
        }


        println()


        regions.forEach { r -> run {
            println("region ${r.plots.first().value} -  ${r.area()} * ${r.numberOfSides(this)} = ${r.area() * r.numberOfSides(this)}")
        } }

    }

    fun plot(c: Coordinates): Plot {
        return grid[c.y][c.x]
    }

    fun findRegions() {
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                val plot = grid[y][x]
                plot.numberOfFences = calcNumFences(plot)
                if (!plot.isInKnownRegion) {
                    regions.add(Region(findRegionOfPlot(plot)))
                }
            }
        }
    }

    private fun findConnectedPlots(plot: Plot): Set<Plot> {
        var result = mutableSetOf<Plot>()
        if(isInBounds(plot.c.up())) {
            val nextPlot = plot(plot.c.up())
            if (!nextPlot.isInKnownRegion && nextPlot.value == plot.value) {
                result.add(nextPlot)
                nextPlot.isInKnownRegion = true
                result.addAll(findConnectedPlots(nextPlot))
            }
        }
        if(isInBounds(plot.c.down())) {
            val nextPlot = plot(plot.c.down())
            if (!nextPlot.isInKnownRegion && nextPlot.value == plot.value) {
                result.add(nextPlot)
                nextPlot.isInKnownRegion = true
                result.addAll(findConnectedPlots(nextPlot))
            }
        }
        if(isInBounds(plot.c.left())) {
            val nextPlot = plot(plot.c.left())
            if (!nextPlot.isInKnownRegion && nextPlot.value == plot.value) {
                result.add(nextPlot)
                nextPlot.isInKnownRegion = true
                result.addAll(findConnectedPlots(nextPlot))
            }
        }
        if(isInBounds(plot.c.right())) {
            val nextPlot = plot(plot.c.right())
            if (!nextPlot.isInKnownRegion && nextPlot.value == plot.value) {
                result.add(nextPlot)
                nextPlot.isInKnownRegion = true
                result.addAll(findConnectedPlots(nextPlot))
            }
        }
        return result
    }

    private fun findRegionOfPlot(plot: Plot): Set<Plot> {
        var plotsInRegion = mutableSetOf<Plot>()

        plotsInRegion.add(plot)
        plot.isInKnownRegion = true
        plotsInRegion.addAll(findConnectedPlots(plot))

        return plotsInRegion
    }

    fun calcNumFences(p : Plot): Int {
        val plotValue = grid[p.c.y][p.c.x]
        var fences = 0
        if(isInBounds(p.c.up())) {
            val nextPlot = plot(p.c.up())
            if (nextPlot.value != plotValue.value) {
               fences++
                p.hasFenceUp = true
            }
        } else {
            fences++
            p.hasFenceUp = true
        }
        if(isInBounds(p.c.down())) {
            val nextPlot = plot(p.c.down())
            if (nextPlot.value != plotValue.value) {
                fences++
                p.hasFenceDown = true
            }
        }else {
            fences++
            p.hasFenceDown = true
        }
        if(isInBounds(p.c.left())) {
            val nextPlot = plot(p.c.left())
            if (nextPlot.value != plotValue.value) {
                fences++
                p.hasFenceLeft = true
            }
        }else {
            fences++
            p.hasFenceLeft = true
        }
        if(isInBounds(p.c.right())) {
            val nextPlot = plot(p.c.right())
            if (nextPlot.value != plotValue.value) {
                fences++
                p.hasFenceRight = true
            }
        }else {
            fences++
            p.hasFenceRight = true
        }

        return fences
    }

    fun isInBounds(c: Coordinates): Boolean {
        return c.x in 0..<grid[0].count() && c.y in 0..<grid.count()
    }
}

class Region(val plots: Set<Plot>) {

    fun perimeter(): Int {
        return plots.sumOf { p -> p.numberOfFences }
    }

    fun area (): Int {
        return plots.count()
    }

    fun numberOfSides(garden: Garden): Int {
        val innerCorners = plots.sumOf { p -> p.numCorners()}

        val outerCorners = plots.sumOf { p -> p.numOuterCorners(garden)}

        return innerCorners + outerCorners
    }
}

class Plot(val c: Coordinates, val value: Char) {
    var numberOfFences = 0
    var isInKnownRegion = false

    var hasFenceUp = false
    var hasFenceDown = false
    var hasFenceLeft = false
    var hasFenceRight = false

    fun numCorners(): Int {
        var corners = 0
        if(hasFenceUp && hasFenceLeft) corners++
        if(hasFenceUp && hasFenceRight) corners++
        if(hasFenceDown && hasFenceLeft) corners++
        if(hasFenceDown && hasFenceRight) corners++
        return corners
    }

    fun numOuterCorners(garden: Garden): Int {
        var corners = 0
        if(!hasFenceUp && !hasFenceRight) {
            val diagonalCoordinates = c.up().right()
            if(garden.isInBounds(diagonalCoordinates)) {
                if(garden.plot(diagonalCoordinates).value != value) corners++
            }
        }
        if(!hasFenceUp && !hasFenceLeft) {
            val diagonalCoordinates = c.up().left()
            if(garden.isInBounds(diagonalCoordinates)) {
                if(garden.plot(diagonalCoordinates).value != value) corners++
            }
        }

        if(!hasFenceDown && !hasFenceRight) {
            val diagonalCoordinates = c.down().right()
            if(garden.isInBounds(diagonalCoordinates)) {
                if(garden.plot(diagonalCoordinates).value != value) corners++
            }
        }

        if(!hasFenceDown && !hasFenceLeft) {
            val diagonalCoordinates = c.down().left()
            if(garden.isInBounds(diagonalCoordinates)) {
                if(garden.plot(diagonalCoordinates).value != value) corners++
            }
        }
        return corners
    }

}

class Coordinates(val x: Int, val y: Int) {
    fun up(): Coordinates {
        return Coordinates(x, y - 1)
    }

    fun down(): Coordinates {
        return Coordinates(x, y + 1)
    }

    fun left(): Coordinates {
        return Coordinates(x - 1, y)
    }

    fun right(): Coordinates {
        return Coordinates(x + 1, y)
    }
}

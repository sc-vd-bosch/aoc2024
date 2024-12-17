package org.example.day12.a

import java.io.File

fun main() {
    val input = File("src/main/resources/day12a.txt").readLines()


    val garden = parseInputToGarden(input)

    garden.findRegions()
    val priceOfFences = garden.regions.sumOf { r -> r.area() * r.perimeter() }
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
                print("plot ($x,$y)  ${plot.value} ${plot.numberOfFences}  - ")
            }
            println()
        }


        println()


        regions.forEach { r -> run {
            println("region ${r.plots.first().value} -  ${r.area()} * ${r.perimeter()} = ${r.area() * r.perimeter()}")
        } }

    }

    fun plot(c: Coordinates): Plot {
        return grid[c.y][c.x]
    }

    fun findRegions() {
        for (y in grid.indices) {
            for (x in grid[0].indices) {
                val plot = grid[y][x]
                plot.numberOfFences = calcNumFences(plot.c)
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

    fun calcNumFences(c: Coordinates): Int {
        val plotValue = grid[c.y][c.x]
        var fences = 0
        if(isInBounds(c.up())) {
            val nextPlot = plot(c.up())
            if (nextPlot.value != plotValue.value) {
               fences++
            }
        } else {
            fences++
        }
        if(isInBounds(c.down())) {
            val nextPlot = plot(c.down())
            if (nextPlot.value != plotValue.value) {
                fences++
            }
        }else {
            fences++
        }
        if(isInBounds(c.left())) {
            val nextPlot = plot(c.left())
            if (nextPlot.value != plotValue.value) {
                fences++
            }
        }else {
            fences++
        }
        if(isInBounds(c.right())) {
            val nextPlot = plot(c.right())
            if (nextPlot.value != plotValue.value) {
                fences++
            }
        }else {
            fences++
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
}

class Plot(val c: Coordinates, val value: Char) {
    var numberOfFences = 0
    var isInKnownRegion = false

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

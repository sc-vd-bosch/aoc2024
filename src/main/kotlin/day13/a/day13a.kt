package org.example.day13.a

import java.io.File

fun main() {
    var input = File("src/main/resources/day13a.txt").readLines()


    val arcade = Arcade.parse(input)
    arcade.print()

    println(arcade.minimumAmountOfCoinsToWinAll())

}

class Arcade(private val clawMachines: List<ClawMachine>) {

    fun print() {
        clawMachines.forEach { cm -> cm.print() }
    }

    fun minimumAmountOfCoinsToWinAll(): Int {
        return clawMachines.sumOf { cm -> cm.minimumAmountOfCoinsToWin() }
    }

    companion object {
        fun parse(input: List<String>): Arcade {
            val clawMachines = mutableListOf<ClawMachine>()
            var inputList = input.toMutableList()
            while(inputList.isNotEmpty()) {
                clawMachines.add(ClawMachine.parse(inputList.subList(0, 4.coerceAtMost(inputList.count()))))
                inputList = inputList.drop(4.coerceAtMost(inputList.count())).toMutableList()
            }
            return Arcade(clawMachines)
        }
    }
}

class ClawMachine(val a: Button, val b: Button, private val prize: Prize) {
    fun minimumAmountOfCoinsToWin(): Int {
        var min = Int.MAX_VALUE
        for (i in 1..100) {
           min = min.coerceAtMost(clickATimes(i))
        }
        return if(min != Int.MAX_VALUE) min else 0
    }

    private fun clickATimes(i: Int): Int {
//        if(i == 80) {
//            val aCoordinates = a.clickTimes(i)
//            println("aCoordinates: X=${aCoordinates.x} Y=${aCoordinates.y}")
//            if(aCoordinates.x == prize.x && aCoordinates.y == prize.y) return i * a.coinsPerClick
//            if(aCoordinates.x < prize.x && aCoordinates.y < prize.y) {
//                //figure out what to do with B
//                val diffX = prize.x - aCoordinates.x
//                val diffY = prize.y - aCoordinates.y
//                println("diffX = $diffX, diffY = $diffY")
//                println("${diffX} % ${b.x} = ${diffX % b.x}, ${diffY} % ${b.y} = ${diffY % b.y}")
//                if(diffX % b.x == 0 && diffY % b.y == 0 ){
//                    val timesXInDiff = diffX / b.x
//                    val timesYInDiff = diffY / b.y
//                    println("timesXInDiff = $timesXInDiff, timesYInDiff = $timesYInDiff")
//
//                    if(timesYInDiff == timesXInDiff) return i * a.coinsPerClick + ((timesXInDiff) * b.coinsPerClick)
//                }
//
//            }
//            return Int.MAX_VALUE
//        }
        val aCoordinates = a.clickTimes(i)
        if(aCoordinates.x == prize.x && aCoordinates.y == prize.y) return i * a.coinsPerClick
        if(aCoordinates.x < prize.x && aCoordinates.y < prize.y) {
            //figure out what to do with B
            val diffX = prize.x - aCoordinates.x
            val diffY = prize.y - aCoordinates.y
            if(diffX % b.x == 0 && diffY % b.y == 0 ){
                val timesXInDiff = diffX / b.x
                val timesYInDiff = diffY / b.y

                if(timesYInDiff == timesXInDiff) return i * a.coinsPerClick + ((timesXInDiff) * b.coinsPerClick)
            }

        }
        return Int.MAX_VALUE
    }

    fun print() {
        println("Button A: X+${a.x}, Y+${a.y}")
        println("Button B: X+${b.x}, Y+${b.y}")
        println("Prize: X=${prize.x}, Y=${prize.y}")
        println()
    }

    companion object {
        fun parse(input: List<String>): ClawMachine {
            val a = Button.parse(input[0], 3)
            val b = Button.parse(input[1], 1)

            val prize = Prize.parse(input[2])

            return ClawMachine(a, b, prize)
        }
    }
}

class Button(val x: Int, val y: Int, val coinsPerClick: Int) {

    fun clickTimes(t: Int): Coordinates{
        return Coordinates(t*x, t*y)
    }

    companion object{
        private val regex = Regex(""": X\+([0-9]+), Y\+([0-9]+)""")
        fun parse(s: String, coinsPerClick: Int): Button {
            val matches = regex.findAll(s)
            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Button(a.toInt(), b.toInt(), coinsPerClick)
        }
    }
}

class Prize(val x: Int, val y: Int){
    companion object{
        private val regex = Regex(""": X=([0-9]+), Y=([0-9]+)""")
        fun parse(s: String): Prize {
            val matches = regex.findAll(s)

            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Prize(a.toInt(), b.toInt())
        }
    }
}

data class Coordinates(val x: Int, val y: Int)
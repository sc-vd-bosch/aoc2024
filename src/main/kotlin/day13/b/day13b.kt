package org.example.day13.b

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

    fun minimumAmountOfCoinsToWinAll(): Long {
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

class ClawMachine(private val aButton: Button, private val bButton: Button, private val prize: Prize) {
    fun minimumAmountOfCoinsToWin(): Long {
        val a = (prize.x * bButton.y - prize.y * bButton.x) / (aButton.x * bButton.y - aButton.y * bButton.x)
        val b = (prize.x - aButton.x * a) / bButton.x
        return if (a * aButton.x + b * bButton.x == prize.x && a * aButton.y + b * bButton.y == prize.y) aButton.coinsPerClick * a + bButton.coinsPerClick * b else 0
    }

    fun print() {
        println("Button A: X+${aButton.x}, Y+${aButton.y}")
        println("Button B: X+${bButton.x}, Y+${bButton.y}")
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

class Button(val x: Long, val y: Long, val coinsPerClick: Long) {
    companion object{
        private val regex = Regex(""": X\+([0-9]+), Y\+([0-9]+)""")
        fun parse(s: String, coinsPerClick: Long): Button {
            val matches = regex.findAll(s)
            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Button(a.toLong(), b.toLong(), coinsPerClick)
        }
    }
}

class Prize(val x: Long, val y: Long){
    companion object{
        private val regex = Regex(""": X=([0-9]+), Y=([0-9]+)""")
        fun parse(s: String): Prize {
            val matches = regex.findAll(s)

            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Prize(a.toLong() + 10000000000000L, b.toLong() + 10000000000000L)
            //                           2318682584
        }
    }
}

data class Coordinates(val x: Long, val y: Long)
package org.example.day13.b

import java.io.File

fun main() {
    var input = File("src/main/resources/day13a-example.txt").readLines()


    val arcade = Arcade.parse(input)
//    arcade.print()

    println(arcade.calc())
//    println(arcade.minimumAmountOfCoinsToWinAll())
}




class Arcade(private val clawMachines: List<ClawMachine>) {

    //a.x * i + b.x * j = prize.x && a.y * i + b.y * j = prize.y

    fun calc(): Long {
        return clawMachines.sumOf { cm -> cm.calc() }
    }

    fun print() {
        clawMachines.forEach { cm -> cm.print() }
    }

//    fun minimumAmountOfCoinsToWinAll(): Long {
//        return clawMachines.sumOf { cm -> cm.calc() }
//    }

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

    fun calc(): Long {
        println("start calc a(${a.x}, ${a.y}) b(${b.x}, ${b.y}) -> prize(${prize.x}, ${prize.y}) ")
        val bTimes = ((a.x * prize.y) - (a.y * prize.x)) / ((a.x * b.y) - (a.y * b.x))
        var bla = ((a.x * prize.y) - (a.y * prize.x)) % ((a.x * b.y) - (a.y * b.x))
        println("bTimes: $bTimes  $bla")
        if(bTimes % 1.0 == 0.000000000000000000000000) {
            val bCost = bTimes.toLong() * b.coinsPerClick

            if(prize.)

            val aCost = 0 * a.coinsPerClick


            return bCost
        }

        return 0L

    }


//    fun calc(): Long {
//       var subA = calcSubA()
//        var subB = calcSubB()
//        return subA.coerceAtMost(subB)
//    }
//
//    fun calcSubB(): Long {
//        var bTimes = (prize.y - (a.y * ((prize.x/a.x) - (b.x/a.x)) ) ) / b.y
//        if(bTimes > bTimes.toLong()) return 0L
//        var aTimes = (prize.x/a.x) - ((b.x/a.x)*bTimes)
//
//        return (aTimes.toLong() * a.coinsPerClick) + (bTimes.toLong() * b.coinsPerClick)
//    }
//
//    fun calcSubA(): Long {
//        var aTimes = (prize.y - (a.y * ((prize.x/a.x) - (b.x/a.x)) ) ) / b.y
//        if(bTimes > bTimes.toLong()) return 0L
//        var bTimes = (prize.y/b.x) - ((a.x/b.x)*aTimes)
//
//        return (aTimes.toLong() * a.coinsPerClick) + (bTimes.toLong() * b.coinsPerClick)
//    }
    

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

class Button(val x: Double, val y: Double, val coinsPerClick: Long) {
    companion object{
        private val regex = Regex(""": X\+([0-9]+), Y\+([0-9]+)""")
        fun parse(s: String, coinsPerClick: Long): Button {
            val matches = regex.findAll(s)
            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Button(a.toDouble(), b.toDouble(), coinsPerClick)
        }
    }
}

class Prize(val x: Double, val y: Double){
    companion object{
        private val regex = Regex(""": X=([0-9]+), Y=([0-9]+)""")
        fun parse(s: String): Prize {
            val matches = regex.findAll(s)

            if(matches.count() != 1) throw Error("Match function does not work")
            val match = matches.first()
            val (a, b) = match.destructured
            return Prize(a.toDouble(), b.toDouble())
            //                           2318682584
        }
    }
}

data class Coordinates(val x: Double, val y: Double)



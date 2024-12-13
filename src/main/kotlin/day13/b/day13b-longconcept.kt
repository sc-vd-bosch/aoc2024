//package org.example.day13.b
//
//import java.io.File
//
//fun main() {
//    var input = File("src/main/resources/day13a-example.txt").readLines()
//
//
//    val arcade = Arcade.parse(input)
//    arcade.print()
//
//    println(arcade.minimumAmountOfCoinsToWinAll())
//
//}
//
//class Arcade(private val clawMachines: List<ClawMachine>) {
//
//    fun print() {
//        clawMachines.forEach { cm -> cm.print() }
//    }
//
//    fun minimumAmountOfCoinsToWinAll(): Long {
//        return clawMachines.sumOf { cm -> cm.minimumAmountOfCoinsToWin() }
//    }
//
//    companion object {
//        fun parse(input: List<String>): Arcade {
//            val clawMachines = mutableListOf<ClawMachine>()
//            var inputList = input.toMutableList()
//            while(inputList.isNotEmpty()) {
//                clawMachines.add(ClawMachine.parse(inputList.subList(0, 4.coerceAtMost(inputList.count()))))
//                inputList = inputList.drop(4.coerceAtMost(inputList.count())).toMutableList()
//            }
//            return Arcade(clawMachines)
//        }
//    }
//}
//
//class ClawMachine(val a: Button, val b: Button, private val prize: Prize) {
//    fun minimumAmountOfCoinsToWin(): Long {
//        var min = Long.MAX_VALUE
//
//        val maxA = findMaxAInPrize()
//
//        var i = maxA
////        for (i in 1L..100L) {
//        while (min == Long.MAX_VALUE) {
//            min = min.coerceAtMost(clickATimes(i))
//            i--
//        }
//        return min.coerceAtLeast(0L)
//    }
//
//    private fun findMaxAInPrize(): Long {
//
//        val timesXInPrize = prize.x / a.x
//        val timesYInPrize = prize.y / a.y
//
//        return(timesXInPrize.coerceAtMost(timesYInPrize))
//    }
//
//    private fun clickATimes(i: Long): Long {
//        val aCoordinates = a.clickTimes(i)
//        if(aCoordinates.x == prize.x && aCoordinates.y == prize.y) return i * a.coinsPerClick
//        if(aCoordinates.x < prize.x && aCoordinates.y < prize.y) {
////                        println("aCoordinates: X=${aCoordinates.x} Y=${aCoordinates.y}")
//
//            //figure out what to do with B
//            val diffX = prize.x - aCoordinates.x
//            val diffY = prize.y - aCoordinates.y
//            if(diffX % b.x == 0L && diffY % b.y == 0L ){
//                val timesXInDiff = diffX / b.x
//                val timesYInDiff = diffY / b.y
//
//                if(timesYInDiff == timesXInDiff) return i * a.coinsPerClick + ((timesXInDiff) * b.coinsPerClick)
//            }
//
//        } else {
//            return -1L
//        }
//        return Long.MAX_VALUE
//    }
//
//    fun print() {
//        println("Button A: X+${a.x}, Y+${a.y}")
//        println("Button B: X+${b.x}, Y+${b.y}")
//        println("Prize: X=${prize.x}, Y=${prize.y}")
//        println()
//    }
//
//    companion object {
//        fun parse(input: List<String>): ClawMachine {
//            val a = Button.parse(input[0], 3)
//            val b = Button.parse(input[1], 1)
//
//            val prize = Prize.parse(input[2])
//
//            return ClawMachine(a, b, prize)
//        }
//    }
//}
//
//class Button(val x: Long, val y: Long, val coinsPerClick: Long) {
//
//    fun clickTimes(t: Long): Coordinates{
//        return Coordinates(t*x, t*y)
//    }
//
//    companion object{
//        private val regex = Regex(""": X\+([0-9]+), Y\+([0-9]+)""")
//        fun parse(s: String, coinsPerClick: Long): Button {
//            val matches = regex.findAll(s)
//            if(matches.count() != 1) throw Error("Match function does not work")
//            val match = matches.first()
//            val (a, b) = match.destructured
//            return Button(a.toLong(), b.toLong(), coinsPerClick)
//        }
//    }
//}
//
//class Prize(val x: Long, val y: Long){
//    companion object{
//        private val regex = Regex(""": X=([0-9]+), Y=([0-9]+)""")
//        fun parse(s: String): Prize {
//            val matches = regex.findAll(s)
//
//            if(matches.count() != 1) throw Error("Match function does not work")
//            val match = matches.first()
//            val (a, b) = match.destructured
//            return Prize(a.toLong() + 10000000000000L, b.toLong() + 10000000000000L)
//            //                           2318682584
//        }
//    }
//}
//
//data class Coordinates(val x: Long, val y: Long)
package org.example.day11.a

import java.io.File

fun main() {
    val input = File("src/main/resources/day11a.txt").readLines()

    val stones = mutableListOf<Stone>()

    val row = input[0]

    row.split(' ').forEach { s ->
//            println(s)
            stones.add(Stone(s.toLong()))
        }

    val stoneRow = StoneRow(stones)

//    stoneRow.print()

    for (i in 0 until 25) {
        stoneRow.blink()
//        stoneRow.print()
    }


    println(stoneRow.getNumberOfStones())
}

class StoneRow(private var stones: List<Stone>){

    fun print() {
        stones.forEach { s -> run {
            print(' ')
            print(s.value)
        } }
        println()
    }

    fun blink() {
        val resultOfBlink = mutableListOf<Stone>()

        stones.forEach { s -> resultOfBlink.addAll(s.blink()) }

        stones = resultOfBlink
    }

    fun getNumberOfStones(): Int {
        return stones.count()
    }
}

class Stone(val value : Long) {
    fun blink(): List<Stone> {
        if (value == 0L) {
            return listOf(Stone(1L))
        }
        val stringValue = value.toString()
//        println("stirngValue $stringValue")
        if (stringValue.count() % 2 == 0){
            val half = stringValue.count()/2
//            println("half $half")
            val value1 = stringValue.substring(0..<half)
//            println("value1 $value1")
            val value2 = stringValue.substring(half..<stringValue.count())
//            println("value2 $value2")
            return listOf(Stone(value1.toLong()), Stone(value2.toLong()) )
        }

        return listOf(Stone(value * 2024L))
    }
}

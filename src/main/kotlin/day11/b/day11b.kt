package org.example.day11.b

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


    var output = stoneRow.blink(75)

//    stoneRow.stones.forEachIndexed {index, stone -> run {
//        val tempStoneRow = StoneRow(listOf(stone))
//        for (i in 0 until 75) {
//            println("$index: $i")
//            tempStoneRow.blink()
////        stoneRow.print()
//        }
//        output += tempStoneRow.getNumberOfStones()
//    }}


    println(output)
}

data class Key(val times: Int, val value: Long)

class StoneRow(var stones: List<Stone>){

    val map = mutableMapOf<Key, Long>()

    fun print() {
        stones.forEach { s -> run {
            print(' ')
            print(s.value)
        } }
        println()
    }

//    fun blink() {
//        val resultOfBlink = mutableListOf<Stone>()
//
//        stones.forEach { s -> resultOfBlink.addAll(s.blink()) }
//
//        stones = resultOfBlink
//    }

    fun blink(times: Int): Long {
       return stones.sumOf { s -> blink(times, s) }
    }

    fun blink(times: Int, stone: Stone): Long {
        val cachedResult = map.getOrDefault(Key(times, stone.value), null)
        if (cachedResult != null) {
            println("found cached result")
            return cachedResult
        }
        val result = stone.blink()
        if(times == 1) {
            return result.count().toLong()
        }
        val resultCount = result.sumOf { s -> blink(times-1, s) }

        map[Key(times,stone.value)] = resultCount

        return resultCount
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

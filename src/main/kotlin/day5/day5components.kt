package org.example.day5

data class Rule(val before: Int, val after: Int)

class Print(
    var pages: MutableList<Int>
) {
    fun isValid(rules: List<Rule>): Boolean {
        return rules.all { r ->
            val afterIndex = pages.indexOf(r.after)
            afterIndex == -1 || pages.indexOf(r.before) < afterIndex
        }
    }

    fun makeValid(rules: List<Rule>) : Print {
        val toSort = pages.toMutableList()
        val sorted = mutableListOf<Int>()

        while (toSort.isNotEmpty()) {
            val firstFree = toSort.first { i ->
                rules.filter { r -> r.after == i }
                    .filter { r -> pages.contains(r.before) }
                    .all { r -> sorted.contains(r.before) }
            }

            sorted.add(firstFree)
            toSort.remove(firstFree)
        }

        return Print(sorted)
    }

    fun middlePage(): Int {
        return pages[pages.count()/2]
    }

    companion object {
        fun new(s: String) = Print( s.split(',').map { p -> p.toInt() }.toMutableList())
    }

}
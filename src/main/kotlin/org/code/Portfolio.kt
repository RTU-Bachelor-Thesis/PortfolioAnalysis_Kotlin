package org.code

data class Portfolio(val data: List<List<Double>>) {
    fun transpose(): Portfolio =
        Portfolio(data[0].indices.map { col -> data.map { it[col] } })

    fun scale(coefficient: Double): Portfolio =
        Portfolio(data.map { row -> row.map { it * coefficient } })

    fun calculateReturnChange(): Portfolio =
        Portfolio(data.map { row -> row.zipWithNext { a, b -> b - a } })

    fun applyWeights(weights: Portfolio): Portfolio =
        Portfolio(data.zip(weights.data).map { (row, weightRow) ->
            row.zip(weightRow).map { it.first * it.second }
        })

    fun findAssetsWithinReturnRange(period: Int, min: Double, max: Double): List<Int> =
        data.indices.filter { data[it][period] > min && data[it][period] < max }

    fun findMaxTotalReturn(): Double =
        data.maxOf { it.sum() }

    companion object {
        fun fillRandom(rows: Int, columns: Int, min: Double, max: Double): Portfolio {
            val random = kotlin.random.Random
            val data = List(rows) { List(columns) { random.nextDouble(min, max) } }
            return Portfolio(data)
        }

        fun combine(portfolio1: Portfolio, portfolio2: Portfolio): Portfolio {
            val combinedData = portfolio1.data.zip(portfolio2.data).map { (row1, row2) ->
                row1.zip(row2).map { it.first + it.second }
            }
            return Portfolio(combinedData)
        }

        fun createWeightsDistribution(assets: Int, periods: Int): Portfolio {
            val random = kotlin.random.Random
            val weights = List(periods) { List(assets) { random.nextDouble() } }
            val normalizedWeights = weights.map { row ->
                val sum = row.sum()
                row.map { it / sum }
            }
            return Portfolio(normalizedWeights).transpose()
        }
    }
}

fun Portfolio.print() {
    data.forEach { row ->
        println(row.joinToString(separator = " ") { element -> "%8.2f".format(element) })
    }
}

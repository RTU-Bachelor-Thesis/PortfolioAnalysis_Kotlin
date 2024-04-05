package org.benchmarkings

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import org.code.Portfolio

@State(Scope.Benchmark)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
open class PortfolioBenchmark {
    private lateinit var originalPortfolio: Portfolio
    private lateinit var additionalPortfolio: Portfolio
    private lateinit var weightMatrix: Portfolio

    @Param(value = ["10", "100", "1000"])
    var assetsCount: Int = 0
    var periodsCount: Int = 12

    @Setup(Level.Iteration)
    fun setupPortfolio() {
        originalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
        additionalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
        weightMatrix = Portfolio.createWeightsDistribution(assetsCount, periodsCount)
    }

    @Benchmark
    fun benchmarkFillRandom(): Portfolio =
        Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)

    @Benchmark
    fun benchmarkTranspose(): Portfolio =
        originalPortfolio.transpose()

    @Benchmark
    fun benchmarkScale(): Portfolio =
        originalPortfolio.scale(Math.PI)

    @Benchmark
    fun benchmarkCalculateReturnChange(): Portfolio =
        originalPortfolio.calculateReturnChange()

    @Benchmark
    fun benchmarkCombine(): Portfolio =
        Portfolio.combine(originalPortfolio, additionalPortfolio)

    @Benchmark
    fun benchmarkApplyWeights(): Portfolio =
        originalPortfolio.applyWeights(weightMatrix)

    @Benchmark
    fun benchmarkFindAssetsWithinReturnRange(): List<Int> =
        originalPortfolio.findAssetsWithinReturnRange(5, 2.0, 5.0)

    @Benchmark
    fun benchmarkFindMaxTotalReturn(): Double =
        originalPortfolio.findMaxTotalReturn()
}

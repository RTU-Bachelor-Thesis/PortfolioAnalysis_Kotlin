package org.code

fun main() {
    val assetsCount = 10
    val periodsCount = 12

    val originalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
    println("\nOriginal profitability portfolio (1): ")
    originalPortfolio.print()

    val transposedPortfolio = originalPortfolio.transpose()
    println("\nTransposed portfolio: ")
    transposedPortfolio.print()

    val scaledPortfolio = originalPortfolio.scale(Math.PI)
    println("\nScaled portfolio: ")
    scaledPortfolio.print()

    val returnChangePortfolio = originalPortfolio.calculateReturnChange()
    println("\nChange in returns on assets: ")
    returnChangePortfolio.print()

    val additionalPortfolio = Portfolio.fillRandom(assetsCount, periodsCount, -10.0, 10.0)
    println("\nAdditional profitability portfolio (2): ")
    additionalPortfolio.print()

    val combinedPortfolios = Portfolio.combine(originalPortfolio, additionalPortfolio)
    println("\nTotal profitability of portfolios: ")
    combinedPortfolios.print()

    val weightMatrix = Portfolio.createWeightsDistribution(assetsCount, periodsCount)
    println("\nWeight matrix: ")
    weightMatrix.print()

    val weightedPortfolio = originalPortfolio.applyWeights(weightMatrix)
    println("\nWeighted portfolio: ")
    weightedPortfolio.print()

    val rangeFilteredAssets = originalPortfolio.findAssetsWithinReturnRange(5, 2.0, 5.0)
    println("\nIndexes of assets with returns in June greater than 2 and less than 5: ${rangeFilteredAssets.joinToString(", ")}")

    val maxYearProfitability = originalPortfolio.findMaxTotalReturn()
    println("\nMaximum year profitability: %.2f".format(maxYearProfitability))
}

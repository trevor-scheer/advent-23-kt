import kotlin.math.max

fun main() {
    partOne()
    partTwo()
}

private fun buildCards(): Array<Card> {
    val lines = input.split("\n")
    lines.forEach { println(it.substring(9)) }

    return lines
        .map { it.substring(9) }
        .fold<String, Array<Card>>(emptyArray<Card>()) { acc, line ->
            val (winnersString, candidatesString) = line.split("|")
            val winners = winnersString.trim().split("\\s+".toRegex()).map { it.toInt() }.toHashSet()
            val candidates = candidatesString.trim().split("\\s+".toRegex()).map { it.toInt() }.toHashSet()
            acc.plus(Card(winners, candidates, 1))
        }
}

fun partOne() {
    val cards = buildCards()
    val solution = cards.fold(0) { acc, card ->
        var pts = 0
        for (winner in card.winners) {
            if (card.candidates.contains(winner)) {
                pts = max(pts * 2, 1)
            }
        }
        acc + pts
    }
    println(solution)
}

fun partTwo() {
    val cards = buildCards()
    cards.forEachIndexed { index, card ->
        val winnersCount = card.winners.fold(0) { acc, winner ->
            if (card.candidates.contains(winner)) {
                acc + 1
            } else {
                acc
            }
        }

        for (i in index + 1..index + winnersCount) {
            if (i < cards.size) {
                cards[i].count += card.count
            }
        }
    }
    println(cards.fold(0){ acc, card -> acc + card.count })
}

data class Card(val winners: HashSet<Int>, val candidates: HashSet<Int>, var count: Int)
import kotlin.math.max

data class RGB(val r: Int, val g: Int, val b: Int)

fun main() {
    partOne()
    partTwo()
}

fun rgbMaxesByRound(): MutableMap<Int, RGB> {
    val lines = input.split("\n")
    val rgbMaxesByRoundId = mutableMapOf<Int, RGB>()
    for (line in lines) {
        var str = line.removePrefix("Game ")
        val id = str.substringBefore(':').toInt()
        str = str.substringAfter(' ')
        val rounds = str.split("; ", ", ")
        val maxes = rounds.fold(RGB(0, 0, 0)) { acc, colorCount ->
            if (colorCount.contains("red"))
                RGB(max(colorCount.substringBefore(' ').toInt(), acc.r), acc.g, acc.b)
            else if (colorCount.contains("green"))
                RGB(acc.r, max(colorCount.substringBefore(' ').toInt(), acc.g), acc.b)
            else
                RGB(acc.r, acc.g, max(colorCount.substringBefore(' ').toInt(), acc.b))
        }
        rgbMaxesByRoundId[id] = maxes
    }
    return rgbMaxesByRoundId
}

fun partOne() {
    val baseline = RGB(12,13,14)
    val rgbMaxesByRoundId = rgbMaxesByRound()
    val solution = rgbMaxesByRoundId.entries.fold(0) { idSum, (id, rgb) ->
        println(idSum)
        if (rgb.r > baseline.r || rgb.b > baseline.b || rgb.g > baseline.g) idSum
        else idSum + id
    }
    println(solution)
}

fun partTwo() {
    val rgbMaxesByRoundId = rgbMaxesByRound()
    val solution = rgbMaxesByRoundId.values.fold(0) { powerSum, (r, g, b) -> powerSum + (r * g * b) }
    println(solution)
}
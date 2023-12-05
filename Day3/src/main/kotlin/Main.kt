fun main() {
    partOne()
    partTwo()
}

private fun partOne() {
    val lines = input.split("\n")
    var partNumbers = emptyArray<Int?>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, character ->
            // we found a symbol, let's look around
            if ("!@#$%^&*()-_=+/".contains(character)) {
                // center above
                val centerAbove = getNumberFromCoordinate(lines, row - 1, column)
                if (row > 0) partNumbers += centerAbove
                // center below
                val centerBelow = getNumberFromCoordinate(lines, row + 1, column)
                if (row < lines.size - 1) partNumbers += centerBelow

                // to the left
                if (column > 0) {
                    partNumbers += getNumberFromCoordinate(lines, row, column - 1) ?: 0
                    // left above
                    if (row > 0 && centerAbove == null) partNumbers += getNumberFromCoordinate(
                        lines,
                        row - 1,
                        column - 1
                    ) ?: 0
                    // left below
                    if (row < lines.size - 1 && centerBelow == null) partNumbers += getNumberFromCoordinate(
                        lines,
                        row + 1,
                        column - 1
                    ) ?: 0
                }
                // to the right
                if (column < line.length - 1) {
                    partNumbers += getNumberFromCoordinate(lines, row, column + 1) ?: 0
                    // right above
                    if (row > 0 && centerAbove == null) partNumbers += getNumberFromCoordinate(
                        lines,
                        row - 1,
                        column + 1
                    ) ?: 0
                    // right below
                    if (row < lines.size - 1 && centerBelow == null) partNumbers += getNumberFromCoordinate(
                        lines,
                        row + 1,
                        column + 1
                    ) ?: 0
                }
            }
        }
    }

    println(partNumbers.filterNotNull().sum())
}

private fun partTwo() {
    val lines = input.split("\n")
    var partNumbers = emptyArray<Int?>()
    var sumOfGearRatios = 0
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { column, character ->
            // we found a symbol, let's look around
            if ("!@#$%^&*()-_=+/".contains(character)) {
                var adjacencies = emptyArray<Int>()
                // center above
                val centerAbove = getNumberFromCoordinate(lines, row - 1, column)
                if (centerAbove != null) adjacencies += centerAbove
                if (row > 0) partNumbers += centerAbove
                // center below
                val centerBelow = getNumberFromCoordinate(lines, row + 1, column)
                if (centerBelow != null) adjacencies += centerBelow
                if (row < lines.size - 1) partNumbers += centerBelow

                // to the left
                if (column > 0) {
                    val left = getNumberFromCoordinate(lines, row, column - 1)
                    if (left != null) {
                        adjacencies += left
                        partNumbers += left
                    }
                    // left above
                    if (row > 0 && centerAbove == null) {
                        val leftAbove = getNumberFromCoordinate(
                            lines,
                            row - 1,
                            column - 1
                        )
                        if (leftAbove != null) adjacencies += leftAbove
                        partNumbers += leftAbove
                    }
                    // left below
                    if (row < lines.size - 1 && centerBelow == null) {
                        val leftBelow = getNumberFromCoordinate(
                            lines,
                            row + 1,
                            column - 1
                        )
                        if (leftBelow != null) adjacencies += leftBelow
                        partNumbers += leftBelow
                    }
                }
                // to the right
                if (column < line.length - 1) {
                    val right = getNumberFromCoordinate(lines, row, column + 1)
                    partNumbers += right
                    if (right != null) adjacencies += right
                    // right above
                    if (row > 0 && centerAbove == null) {
                        val rightAbove = getNumberFromCoordinate(
                            lines,
                            row - 1,
                            column + 1
                        )
                        if (rightAbove != null) adjacencies += rightAbove
                        partNumbers += rightAbove
                    }
                    // right below
                    if (row < lines.size - 1 && centerBelow == null) {
                        val rightBelow = getNumberFromCoordinate(
                            lines,
                            row + 1,
                            column + 1
                        )
                        if (rightBelow != null) adjacencies += rightBelow
                        partNumbers += rightBelow
                    }
                }

                if (character == '*' && adjacencies.size == 2) {
                    sumOfGearRatios += (adjacencies[0] * adjacencies[1])
                }
            }
        }
    }

    println(sumOfGearRatios)
}


fun getNumberFromCoordinate(matrix: List<String>, row: Int, column: Int): Int? {
    var parsed = ""
    if (matrix[row][column].isDigit()) {
        parsed+= matrix[row][column]
        // look left
        var columnInc = column - 1
        while (columnInc >=0 && matrix[row][columnInc].isDigit()) {
            parsed = matrix[row][columnInc--] + parsed
        }
        // look right
        columnInc = column + 1
        while (columnInc < matrix[row].length && matrix[row][columnInc].isDigit()) {
            parsed += matrix[row][columnInc++]
        }
        return parsed.toInt()
    }
    return null
}
package solutions

import FileReader
import solutions.LayoutObject.*

class Day11 : FileReader() {
    fun solution1() {
        val seatLayout = mutableListOf<List<LayoutObject>>()
        inputFile.readLines().forEach {
            val currentRowSeats = mutableListOf<LayoutObject>()

            it.forEach {
                if (it == '.') currentRowSeats.add(Floor)
                if (it == 'L') currentRowSeats.add(Seat())
            }

            seatLayout.add(currentRowSeats)
        }

        var anySeatChanges = true
        while (anySeatChanges) {
            seatLayout.forEachIndexed row@ { rowIndex, row ->
                row.forEachIndexed column@ { columnIndex, layoutObject ->
                    if (layoutObject is Floor) return@column
                    else if (layoutObject is Seat){

                        val adjacentLayoutObjects = mutableListOf(
                            seatLayout.getOrNull(rowIndex - 1, columnIndex - 1),
                            seatLayout.getOrNull(rowIndex - 1, columnIndex),
                            seatLayout.getOrNull(rowIndex - 1, columnIndex + 1),
                            seatLayout.getOrNull(rowIndex, columnIndex - 1),
                            seatLayout.getOrNull(rowIndex, columnIndex + 1),
                            seatLayout.getOrNull(rowIndex + 1, columnIndex - 1),
                            seatLayout.getOrNull(rowIndex + 1, columnIndex),
                            seatLayout.getOrNull(rowIndex + 1, columnIndex + 1)
                        ).filterIsInstance<LayoutObject>()

                        if (!layoutObject.currentlyOccupied) {
                            //Rule 1
                            if (adjacentLayoutObjects.filterIsInstance<Seat>().none { it.currentlyOccupied }) {
                                layoutObject.shouldChangeAtUpdate = true
                            }
                        } else {
                            //Rule 2
                            if (adjacentLayoutObjects.filterIsInstance<Seat>().count { it.currentlyOccupied } >= 4) {
                                layoutObject.shouldChangeAtUpdate = true
                            }
                        }
                    }
                }
            }

            seatLayout.flatten().filterIsInstance<Seat>().let {
                if (it.none { it.shouldChangeAtUpdate }) anySeatChanges = false

                it.forEach { if(it.shouldChangeAtUpdate) it.toggleOccupied() }
            }
        }

        print("Total occupied seats: ${seatLayout.flatten().filterIsInstance<Seat>().count { it.currentlyOccupied }}")
        print("\nDONE :D")
    }

    fun solution2() {
        val seatLayout = mutableListOf<List<LayoutObject>>()
        inputFile.readLines().forEach {
            val currentRowSeats = mutableListOf<LayoutObject>()

            it.forEach {
                if (it == '.') currentRowSeats.add(Floor)
                if (it == 'L') currentRowSeats.add(Seat())
            }

            seatLayout.add(currentRowSeats)
        }

        var anySeatChanges = true
        while (anySeatChanges) {
            seatLayout.forEachIndexed row@ { rowIndex, row ->
                row.forEachIndexed column@ { columnIndex, layoutObject ->
                    if (layoutObject is Floor) return@column
                    else if (layoutObject is Seat){

                        val adjacentLayoutObjects = mutableListOf(
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex,- 1, - 1),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, - 1, 0),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, - 1, + 1),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, 0, - 1),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, 0, + 1),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, + 1, - 1),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, + 1, 0),
                            seatLayout.getFirstSeatInDirectionOrNull(rowIndex, columnIndex, + 1, + 1)
                        ).filterIsInstance<LayoutObject>()

                        if (!layoutObject.currentlyOccupied) {
                            //Rule 1
                            if (adjacentLayoutObjects.filterIsInstance<Seat>().none { it.currentlyOccupied }) {
                                layoutObject.shouldChangeAtUpdate = true
                            }
                        } else {
                            //Rule 2
                            if (adjacentLayoutObjects.filterIsInstance<Seat>().count { it.currentlyOccupied } >= 5) {
                                layoutObject.shouldChangeAtUpdate = true
                            }
                        }
                    }
                }
            }

            seatLayout.flatten().filterIsInstance<Seat>().let {
                if (it.none { it.shouldChangeAtUpdate }) anySeatChanges = false

                it.forEach { if(it.shouldChangeAtUpdate) it.toggleOccupied() }
            }
        }

        print("Total occupied seats: ${seatLayout.flatten().filterIsInstance<Seat>().count { it.currentlyOccupied }}")
        print("\nDONE :D")
    }
}

sealed class LayoutObject {
    data class Seat(
        var currentlyOccupied: Boolean = false,
        var shouldChangeAtUpdate: Boolean = false
    ): LayoutObject() {
        fun toggleOccupied() {
            currentlyOccupied = !currentlyOccupied
            shouldChangeAtUpdate = false
        }
    }

    object Floor: LayoutObject()
}

fun <T> List<List<T>>.getOrNull(row: Int, column: Int) : T? {
    return try {
        this[row][column]
    } catch (exception: Exception) {
        null
    }
}

fun <T> List<List<T>>.getFirstSeatInDirectionOrNull(startRow: Int, startColumn:Int, rowChange: Int, columnChange: Int) : Seat? {
    var rowIndex = startRow + rowChange
    var columnIndex = startColumn + columnChange
    var currentLayoutObject = this.getOrNull(rowIndex, columnIndex)

    while(currentLayoutObject != null) {
        if (currentLayoutObject is Seat) return currentLayoutObject

        rowIndex += rowChange
        columnIndex += columnChange
        currentLayoutObject = this.getOrNull(rowIndex, columnIndex)
    }

    return null
}
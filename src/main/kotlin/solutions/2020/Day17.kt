package solutions

import FileReader

class Day17 : FileReader() {
    fun solution1() {
        //NOTE: the top-left most point in the starting input is coords 0, 0, 0
        val pocketDimension = mutableListOf<ConwayCube>()

        //set up starting locations
        inputFile.readLines().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                pocketDimension.add(ConwayCube(PocketDimensionCoordinate(x, y, 0), active = char == '#'))
            }
        }

        var tmpList = mutableListOf<ConwayCube>().apply { addAll(pocketDimension) }
        tmpList.forEach { it.findNeighbors(pocketDimension) }

        for (cycle in 0 until 6) {
            tmpList = mutableListOf<ConwayCube>().apply { addAll(pocketDimension) }

            tmpList.forEach {
                val activeNeighbors = it.findNeighbors(pocketDimension).count { it.active }

                if (it.active) {
                    if (activeNeighbors !in 2..3) it.shouldToggleAtUpdate = true
                } else {
                    if (activeNeighbors == 3) it.shouldToggleAtUpdate = true
                }
            }

            //Update all Cubes simultaneously
            pocketDimension.forEach { if (it.shouldToggleAtUpdate) it.toggleCubeActive() }
        }

        print("Active cube count: ${pocketDimension.count { it.active }}")
        print("\nDONE :D")
    }

    fun solution2() {
        //NOTE: the top-left most point in the starting input is coords 0, 0, 0
        val pocketDimension = mutableListOf<ConwayHypercube>()

        //set up starting locations
        inputFile.readLines().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                pocketDimension.add(ConwayHypercube(PocketDimensionHyperCoordinate(x, y, 0, 0), active = char == '#'))
            }
        }

        var tmpList = mutableListOf<ConwayHypercube>().apply { addAll(pocketDimension) }
        tmpList.forEach { it.findNeighbors(pocketDimension) }

        for (cycle in 0 until 6) {
            print("Running Turn: ${cycle + 1}\n")

            tmpList = mutableListOf<ConwayHypercube>().apply { addAll(pocketDimension) }

            tmpList.forEachIndexed { index, cube ->
                print("Processing Cube: ${index + 1} of ${tmpList.size}\n")

                val activeNeighbors = cube.findNeighbors(pocketDimension).count { it.active }

                if (cube.active) {
                    if (activeNeighbors !in 2..3) cube.shouldToggleAtUpdate = true
                } else {
                    if (activeNeighbors == 3) cube.shouldToggleAtUpdate = true
                }
            }

            //Update all Cubes simultaneously
            pocketDimension.forEach { if (it.shouldToggleAtUpdate) it.toggleCubeActive() }
        }

        print("Active cube count: ${pocketDimension.count { it.active }}")
        print("\nDONE :D")
    }
}

data class ConwayCube(
    val coordinates: PocketDimensionCoordinate,
    var active: Boolean = false,
    var shouldToggleAtUpdate: Boolean = false
) {
    fun toggleCubeActive() {
        active = !active
        shouldToggleAtUpdate = false
    }
}

fun MutableList<ConwayCube>.getAtCoords(x: Int, y: Int, z: Int): ConwayCube {
    return this.find { it.coordinates.checkDimensions(x, y, z) }
        ?: ConwayCube(PocketDimensionCoordinate(x, y, z)).also { this.add(it) }
}

fun ConwayCube.findNeighbors(cubes: MutableList<ConwayCube>): List<ConwayCube> {
    val neighbors = mutableListOf<ConwayCube>()

    for (xOffset in -1..1) {
        for (yOffset in -1..1) {
            for (zOffset in -1..1) {
                if (xOffset == 0 && yOffset == 0 && zOffset == 0) continue

                neighbors.add(
                    cubes.getAtCoords(
                        coordinates.x + xOffset,
                        coordinates.y + yOffset,
                        coordinates.z + zOffset,
                    )
                )
            }
        }
    }

    return neighbors
}

data class PocketDimensionCoordinate(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun checkDimensions(x: Int, y: Int, z: Int) : Boolean {
        return this.x == x && this.y == y && this.z == z
    }
}

data class ConwayHypercube(
    val coordinates: PocketDimensionHyperCoordinate,
    var active: Boolean = false,
    var shouldToggleAtUpdate: Boolean = false
) {
    fun toggleCubeActive() {
        active = !active
        shouldToggleAtUpdate = false
    }
}

fun MutableList<ConwayHypercube>.getAtCoords(x: Int, y: Int, z: Int, w: Int): ConwayHypercube {
    return this.find { it.coordinates.checkDimensions(x, y, z, w) }
        ?: ConwayHypercube(PocketDimensionHyperCoordinate(x, y, z, w)).also { this.add(it) }
}

fun ConwayHypercube.findNeighbors(cubes: MutableList<ConwayHypercube>): List<ConwayHypercube> {
    val neighbors = mutableListOf<ConwayHypercube>()

    for (xOffset in -1..1) {
        for (yOffset in -1..1) {
            for (zOffset in -1..1) {
                for (wOffset in -1..1) {
                    if (xOffset == 0 && yOffset == 0 && zOffset == 0 && wOffset == 0) continue

                    neighbors.add(
                        cubes.getAtCoords(
                            coordinates.x + xOffset,
                            coordinates.y + yOffset,
                            coordinates.z + zOffset,
                            coordinates.w + wOffset
                        )
                    )
                }
            }
        }
    }

    return neighbors
}

data class PocketDimensionHyperCoordinate(
    val x: Int,
    val y: Int,
    val z: Int,
    val w: Int
) {
    fun checkDimensions(x: Int, y: Int, z: Int, w: Int) : Boolean {
        return this.x == x && this.y == y && this.z == z && this.w == w
    }
}
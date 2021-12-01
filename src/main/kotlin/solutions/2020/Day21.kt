package solutions

import FileReader

class Day21 : FileReader() {
    fun solution1() {
        val foods = mutableListOf<Pair</* Ingredients */List<String>, /* Allergens */List<String>>>()

        inputFile.readLines().forEach {
            it.split(" (").let { (ingredients, allergens) ->
                foods.add(ingredients.split(" ") to allergens.trim(')').removePrefix("contains ").split(", "))
            }
        }

        val allAllergens = foods.map { it.second }.flatten().distinct()

        val allergenAssignmentList = mutableListOf<Allergen>()

        allAllergens.forEach { allergen ->
            allergenAssignmentList.add(
                Allergen(
                    allergen,
                    foods.filter { (_, allergens) -> allergens.contains(allergen) }.map { it.first }.intersect().toMutableList()
                )
            )
        }

        while (allergenAssignmentList.map { it.possibleIngredients }.any { it.size > 1 }) {
            val allergenToAssign = allergenAssignmentList.first { it.possibleIngredients.size == 1 && !it.assigned }
            allergenToAssign.assigned = true

            allergenAssignmentList.forEach removeAssigned@ {
                if (it !== allergenToAssign) {
                    it.possibleIngredients.removeAll { it == allergenToAssign.possibleIngredients.first() }
                }
            }
        }

        val nonAllergenicIngredients = foods.map { it.first }.flatten().toMutableList().minus(allergenAssignmentList.map { it.possibleIngredients.first() })

        print("Non-Allergens appear ${nonAllergenicIngredients.size} times in foods")
        print("\nDONE :D")
    }

    fun solution2() {
        val foods = mutableListOf<Pair</* Ingredients */List<String>, /* Allergens */List<String>>>()

        inputFile.readLines().forEach {
            it.split(" (").let { (ingredients, allergens) ->
                foods.add(ingredients.split(" ") to allergens.trim(')').removePrefix("contains ").split(", "))
            }
        }

        val allAllergens = foods.map { it.second }.flatten().distinct()

        val allergenAssignmentList = mutableListOf<Allergen>()

        allAllergens.forEach { allergen ->
            allergenAssignmentList.add(
                Allergen(
                    allergen,
                    foods.filter { (_, allergens) -> allergens.contains(allergen) }.map { it.first }.intersect().toMutableList()
                )
            )
        }

        while (allergenAssignmentList.map { it.possibleIngredients }.any { it.size > 1 }) {
            val allergenToAssign = allergenAssignmentList.first { it.possibleIngredients.size == 1 && !it.assigned }
            allergenToAssign.assigned = true

            allergenAssignmentList.forEach removeAssigned@ {
                if (it !== allergenToAssign) {
                    it.possibleIngredients.removeAll { it == allergenToAssign.possibleIngredients.first() }
                }
            }
        }

        val dangerousIngredients = allergenAssignmentList.sortedBy { it.name }.map { it.possibleIngredients.first() }.joinToString(",")

        print("Canonical Dangerous Ingredient List: $dangerousIngredients")
        print("\nDONE :D")
    }
}

fun List<List<String>>.intersect(): List<String> {
    var intersection = this[0]

    for (index in 1..lastIndex) {
        intersection = intersection.intersect(this[index]).toList()
    }

    return intersection
}

data class Allergen(
    val name: String,
    val possibleIngredients: MutableList<String>,
    var assigned : Boolean = false
)
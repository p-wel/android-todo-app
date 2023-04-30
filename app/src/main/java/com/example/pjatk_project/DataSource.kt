package com.example.pjatk_project

// obiekt (pojedyncza instancja) na dane
object DataSource {
    val dishes = mutableListOf<Dish>(
        Dish(
            "Pizza margerita", listOf("ser", "sos pomidorowy"), R.drawable.pizza,
        ),
        Dish(
            "Spaghetti", listOf("makaron", "sos pomidorowy"), R.drawable.spaghetti,
        ),
    )
}
package com.example.pjatk_project

import androidx.annotation.DrawableRes

data class Dish(
    val name: String,
    val ingredients: List<String>,
    @DrawableRes
    val resId: Int
)


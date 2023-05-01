package com.example.pjatk_project.model

import androidx.annotation.DrawableRes

data class Task(
    val id: Long,
    val name: String,
    val description: List<String>,
    @DrawableRes
    val resId: Int
)


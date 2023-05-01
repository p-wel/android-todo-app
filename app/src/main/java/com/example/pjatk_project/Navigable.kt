package com.example.pjatk_project

interface Navigable {
    enum class Destination {
        List,
        Add,
        Edit
    }

    fun navigate(to: Destination, id: Long? = null)
}
package com.example.pjatk_project

interface Navigable {
    enum class Destination {
        List,
        Add,
        Edit,
        Details
    }

    fun navigate(destination: Destination, id: Long? = null)
}
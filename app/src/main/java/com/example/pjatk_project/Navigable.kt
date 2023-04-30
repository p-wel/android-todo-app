package com.example.pjatk_project

interface Navigable {
    enum class Destination {
        List, Add
    }

    fun navigate(to: Destination)
}
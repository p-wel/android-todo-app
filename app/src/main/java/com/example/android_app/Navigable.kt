package com.example.android_app

interface Navigable {
    enum class Destination {
        List,
        Add,
        Edit,
        Details
    }

    fun navigate(destination: Destination, id: Long? = null)
}
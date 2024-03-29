package com.example.android_app

import androidx.recyclerview.widget.DiffUtil
import com.example.android_app.model.Task

// callback - usprawni animację przy pracy z danymi na widoku (zmiany bez "mignięcia")
class TaskCallback(val notSorted: List<Task>, val sorted: List<Task>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = notSorted.size

    override fun getNewListSize(): Int = sorted.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] === sorted[newItemPosition] // porównanie obiektów

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] == sorted[newItemPosition] // porównanie zawartości
}
package com.example.pjatk_project

import androidx.recyclerview.widget.DiffUtil
import com.example.pjatk_project.model.Dish

// callback - usprawni animację przy pracy z danymi na widoku (zmiany bez "mignięcia")
class DishCallback(val notSorted: List<Dish>, val sorted: List<Dish>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = notSorted.size

    override fun getNewListSize(): Int = sorted.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] === sorted[newItemPosition] // porównanie obiektów

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        notSorted[oldItemPosition] == sorted[newItemPosition] // porównanie zawartości
}
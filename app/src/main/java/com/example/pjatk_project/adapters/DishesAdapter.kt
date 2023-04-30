package com.example.pjatk_project

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.databinding.ListItemBinding
import com.example.pjatk_project.model.Dish

// holder na dane
class DishViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dish: Dish) {
        binding.name.text = dish.name
        binding.ingredients.text = dish.ingredients.joinToString(",\n")
        binding.image.setImageResource(dish.resId)
    }
}

// adapter łączący widok z danymi
class DishesAdapter : RecyclerView.Adapter<DishViewHolder>() {
    private val data = mutableListOf<Dish>()

    // handler usprawniający pracę między wątkami (wątek danych / wątek UI)
    private val handler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // żeby ListView nie przypiął się do samej listy
        )
        return DishViewHolder(binding)
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = data.size

    // własna metoda podmieniająca listę i powiadamiająca widok o tej zmianie
    fun replace(newData: List<Dish>) {
        val callback = DishCallback(data, newData)
        data.clear()
        data.addAll(newData)
        val result = DiffUtil.calculateDiff(callback) // sprawdzenie, czy itemy są takie same
        handler.post { // użycie handlera do notyfikacji o zmianie danych
            result.dispatchUpdatesTo(this) // aktualizacja danych na this adapterze
        }

    }

    fun sort() {
        val notSorted = data.toList()
        val callback = DishCallback(notSorted, data)
        data.sortBy { it.name }
        val result = DiffUtil.calculateDiff(callback) // sprawdzenie, czy itemy są takie same
        handler.post { // użycie handlera do notyfikacji o zmianie danych
            result.dispatchUpdatesTo(this) // aktualizacja danych na this adapterze
        }
    }
}
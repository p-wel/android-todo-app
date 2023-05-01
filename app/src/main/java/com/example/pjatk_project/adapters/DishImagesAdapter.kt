package com.example.pjatk_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.R
import com.example.pjatk_project.databinding.DishImageBinding

// holder na obrazki
class DishImageViewHolder(val binding: DishImageBinding) : RecyclerView.ViewHolder(binding.root) {

    // bindowanie obrazka o danym resId
    fun bind(resId: Int, isSelected: Boolean) {
        binding.image.setImageResource(resId)
        binding.selectedFrame.visibility =
            if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}

// adapter łączący widok z danymi
class DishImagesAdapter : RecyclerView.Adapter<DishImageViewHolder>() {

    // przypisanie png do listy images
    private val images = listOf(R.drawable.pierogi, R.drawable.pizza)

    // trackowanie zaznaczonego elementu
    private var selectedPosition: Int = 0 // pozycja na liście
    val selectedIdRes: Int
        get() = images[selectedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishImageViewHolder {
        val binding = DishImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // żeby ListView nie przypiął się do samej listy
        )
        return DishImageViewHolder(binding).also { vh ->  // also{} to lambda wykonująca się przed returnem, vh to viewHolder
            binding.root.setOnClickListener {
                setSelected(vh.layoutPosition) // ustawienie selecta na aktualną pozycję w layoucie
            }
        }
    }

    // ustawienie selecta na obrazek
    private fun setSelected(layoutPosition: Int) {
        notifyItemChanged(selectedPosition) // powiadomienie, że poprzednia pozycja się zmieniła
        selectedPosition = layoutPosition // ustawienie selecta na aktualną pozycję
        notifyItemChanged(selectedPosition) // powiadomienie, że aktualna pozycja się zmieniła
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: DishImageViewHolder, position: Int) {
        holder.bind(images[position], position == selectedPosition)
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = images.size

    // wyszukanie indeksu obrazka i ustawienie go za pomocą setSelected()
    fun setSelection(icon: Int?) {
        val index = images.indexOfFirst { it == icon }
        if (index == -1) return // jeśli nie znajdzie, to nie ustawiaj selecta
        setSelected(index)
    }
}
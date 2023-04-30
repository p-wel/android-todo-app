package com.example.pjatk_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        return DishImageViewHolder(binding).also { vh ->  // also{} to lambda wykonująca się przed returnem, vh - viewHolder
            binding.root.setOnClickListener {
                notifyItemChanged(selectedPosition) // powiadomienie, że poprzednia pozycja się zmieniła
                selectedPosition = vh.layoutPosition // ustawienie selecta na aktualną pozycję
                notifyItemChanged(selectedPosition) // powiadomienie, że aktualna pozycja się zmieniła
            }
        }
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: DishImageViewHolder, position: Int) {
        holder.bind(images[position], position == selectedPosition)
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = images.size
}
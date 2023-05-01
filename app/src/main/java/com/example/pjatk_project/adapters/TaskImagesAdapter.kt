package com.example.pjatk_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.R
import com.example.pjatk_project.databinding.TaskImageBinding

// holder na obrazki
class TaskImageViewHolder(val binding: TaskImageBinding) : RecyclerView.ViewHolder(binding.root) {

    // bindowanie obrazka o danym resId
    fun bind(resId: Int, isSelected: Boolean) {
        binding.image.setImageResource(resId)
        binding.selectedFrame.visibility =
            if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}

// adapter łączący widok z danymi
class TaskImagesAdapter : RecyclerView.Adapter<TaskImageViewHolder>() {

    // przypisanie png do listy images
    private val images = listOf(
        R.drawable.done,
        R.drawable.food,
        R.drawable.home,
        R.drawable.important,
        R.drawable.question,
        R.drawable.school,
        R.drawable.work
    )

    // trackowanie zaznaczonego elementu
    private var selectedPosition: Int = 0 // pozycja na liście
    val selectedIdRes: Int
        get() = images[selectedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskImageViewHolder {
        val binding = TaskImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // żeby ListView nie przypiął się do samej listy
        )
        return TaskImageViewHolder(binding).also { vh ->  // also{} to lambda wykonująca się przed returnem, vh to viewHolder
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
    override fun onBindViewHolder(holder: TaskImageViewHolder, position: Int) {
        holder.bind(images[position], position == selectedPosition)
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = images.size

    // wyszukanie indeksu obrazka i ustawienie go za pomocą setSelected()
    fun setSelection(icon: Int?) {
        val index = images.indexOfFirst { it == icon }
        if (index == -1) return // jeśli nie znajdzie, to nie ustawi selecta
        setSelected(index)
    }
}
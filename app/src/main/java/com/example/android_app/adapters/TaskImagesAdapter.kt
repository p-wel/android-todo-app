package com.example.android_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app.R
import com.example.android_app.databinding.TaskImageBinding


// adapter łączący widok z danymi
class TaskImagesAdapter : RecyclerView.Adapter<TaskImageViewHolder>() {

    private val images = defineImages()

    // trackowanie zaznaczonego elementu
    private var selectedPosition = 0
    val selectedIdRes: Int
        get() = images[selectedPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskImageViewHolder {
        val binding = setBinding(parent)
        val viewHolder = TaskImageViewHolder(binding)
        setListeners(binding, viewHolder)

        return viewHolder
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: TaskImageViewHolder, position: Int) {
        holder.bind(images[position], position == selectedPosition)
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = images.size

    // ustawienie selecta na obrazek
    private fun setSelected(layoutPosition: Int) {
        notifyItemChanged(selectedPosition) // powiadomienie, że poprzednia pozycja się zmieniła
        selectedPosition = layoutPosition // ustawienie selecta na aktualną pozycję
        notifyItemChanged(selectedPosition) // powiadomienie, że aktualna pozycja się zmieniła
    }

    // wyszukanie indeksu obrazka i ustawienie go za pomocą setSelected()
    fun setSelection(icon: Int?) {
        val index = images.indexOfFirst { it == icon }
        if (index == -1) return // jeśli nie znajdzie, to nie ustawi selecta
        setSelected(index)
    }

    private fun defineImages(): List<Int> = listOf(
        R.drawable.done,
        R.drawable.school,
        R.drawable.work,
        R.drawable.food,
        R.drawable.home,
        R.drawable.important,
        R.drawable.question
    )

    private fun setBinding(parent: ViewGroup): TaskImageBinding {
        return TaskImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // false, żeby ListView nie przypiął się do samej listy
        )
    }

    private fun setListeners(binding: TaskImageBinding, viewHolder: TaskImageViewHolder){
        binding.root.setOnClickListener {
            setSelected(viewHolder.layoutPosition) // ustawienie selecta na aktualną pozycję w layoucie
        }
    }
}

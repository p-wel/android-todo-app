package com.example.pjatk_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.TaskCallback
import com.example.pjatk_project.databinding.ListItemBinding
import com.example.pjatk_project.model.Task

// adapter łączący widok z danymi
class TasksAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    private val data = mutableListOf<Task>()

    // zmienna przyjmująca Long data id. Jeśli jest pusty, to nic nie robi
    var onItemClick: (Long) -> Unit = { }
    var onItemLongClick: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = setBinding(parent)
        val viewHolder = TaskViewHolder(binding)
        setListeners(binding, viewHolder)

        return viewHolder
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = data.size

    private fun setBinding(parent: ViewGroup): ListItemBinding {
        return ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // żeby ListView nie przypiął się do samej listy
        )
    }

    private fun setListeners(binding: ListItemBinding, viewHolder: TaskViewHolder) {
        // na ViewHolder podpięcie onItemClick z podaniem odpowiedniego id
        binding.root.setOnClickListener {
            onItemClick(data[viewHolder.layoutPosition].id)
        }

        binding.root.setOnLongClickListener(View.OnLongClickListener {
            onItemLongClick(data[viewHolder.layoutPosition].id)
            true
        })
    }

    // własna metoda podmieniająca listę i powiadamiająca widok o tej zmianie
    fun replace(newData: List<Task>) {
        val callback = TaskCallback(data.toList(), newData)
        data.clear()
        data.addAll(newData)
        val result = DiffUtil.calculateDiff(callback) // sprawdzenie, czy itemy są takie same
        result.dispatchUpdatesTo(this) // aktualizacja danych na this adapterze

    }

    fun sort() {
        val notSorted = data.toList()
        val callback = TaskCallback(notSorted, data)
        data.sortBy { it.name.lowercase() }
        val result = DiffUtil.calculateDiff(callback) // sprawdzenie, czy itemy są takie same
        result.dispatchUpdatesTo(this) // aktualizacja danych na this adapterze
    }

    fun removeItem(layoutPosition: Int): Task {
        val task = data.removeAt(layoutPosition)
        notifyItemRemoved(layoutPosition)
        return task
    }
}
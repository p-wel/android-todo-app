package com.example.pjatk_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.databinding.ListItemBinding
import com.example.pjatk_project.model.Task

// holder na dane
class TaskViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        binding.name.text = task.name
        binding.description.text = task.description.joinToString(",\n")
        binding.image.setImageResource(task.resId)
    }
}

// adapter łączący widok z danymi
class TasksAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    private val data = mutableListOf<Task>()

    // zmienna przyjmująca Long. Jeśli jest pusty, to nic nie robi
    var onItemClick: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // żeby ListView nie przypiął się do samej listy
        )
        return TaskViewHolder(binding).also { vh ->
            // na ViewHolder podpięcie onItemClick z podaniem odpowiedniego id
            binding.root.setOnClickListener {
                onItemClick(data[vh.layoutPosition].id)
            }
        }
    }

    // podpinanie ViewHoldera na odpowiednią pozycję
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // określenie ilości elementów w RecyclerView
    override fun getItemCount(): Int = data.size

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
        data.sortBy { it.name }
        val result = DiffUtil.calculateDiff(callback) // sprawdzenie, czy itemy są takie same
        result.dispatchUpdatesTo(this) // aktualizacja danych na this adapterze
    }

    fun removeItem(layoutPosition: Int): Task {
        val task = data.removeAt(layoutPosition)
        notifyItemRemoved(layoutPosition)
        return task
    }
}
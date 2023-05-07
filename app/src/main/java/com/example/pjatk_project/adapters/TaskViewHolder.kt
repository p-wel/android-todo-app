package com.example.pjatk_project.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.databinding.ListItemBinding
import com.example.pjatk_project.model.Task

// holder na dane
class TaskViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        binding.name.text = task.name
        binding.description.text = task.description.joinToString(",\n")
        binding.image.setImageResource(task.resId)
    }
}
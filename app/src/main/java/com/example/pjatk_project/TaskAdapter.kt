package com.example.pjatk_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTaskName: TextView = itemView.findViewById(R.id.taskName)
        val buttonDeleteTask: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Konfiguracja widoku elementu listy
        val task = tasks[position]
        holder.textViewTaskName.text = task.name
        holder.buttonDeleteTask.setOnClickListener {
            // handle delete button click
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}

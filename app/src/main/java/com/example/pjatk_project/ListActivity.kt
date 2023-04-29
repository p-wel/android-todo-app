package com.example.pjatk_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatk_project.databinding.ActivityListBinding
import com.example.pjatk_project.databinding.ActivityMainBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsActions(binding)

        taskList = mutableListOf(
            Task("Task 1"),
            Task("Task 2"),
            Task("Task 3"),
            Task("Task 5"),
            Task("Task 6"),
            Task("Task 7"),
            Task("Task 8"),
            Task("Task 9"),
            Task("Task 10"),
            Task("Task 11"),
            Task("Task 12"),
            Task("Task 13"),
            Task("Task 14")
        )


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = TaskAdapter(taskList)

    }

    fun start(context: Context) {
        context.startActivity(Intent(context, ListActivity::class.java))
    }

    private fun setButtonsActions(binding: ActivityListBinding) {
        binding.buttonBack.setOnClickListener {
            this@ListActivity.finish()
        }
    }


}
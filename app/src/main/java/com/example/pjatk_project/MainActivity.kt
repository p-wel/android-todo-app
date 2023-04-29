package com.example.pjatk_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatk_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonsActions(binding)
    }

    fun start(context: Context) {
        context.startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setButtonsActions(binding: ActivityMainBinding) {
        binding.buttonStart.setOnClickListener {
            ListActivity().start(this)
        }
    }
}
package com.example.pjatk_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatk_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // stworzenie aktywności
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dodanie ListFragment do fragment managera
        val listFragment = ListFragment()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container, listFragment,
                listFragment.javaClass.name //tag, po którym będzie można łatwo znaleźć w fragmetManagerze
            )
            .commit() // wpięcie do managera
    }

    // uruchomienie aktyności
    fun start(context: Context) {
        context.startActivity(Intent(this, MainActivity::class.java))
    }


}
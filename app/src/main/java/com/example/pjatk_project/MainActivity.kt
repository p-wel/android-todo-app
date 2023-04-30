package com.example.pjatk_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatk_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var listFragment: ListFragment

    // stworzenie aktywności
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)

        // dodanie ListFragment do fragment managera
        listFragment = ListFragment()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container, listFragment,
                listFragment.javaClass.name //tag, po którym będzie można łatwo znaleźć w fragmetManagerze
            )
            .commit() // wpięcie do managera
    }

    override fun navigate(to: Navigable.Destination) {
        supportFragmentManager.beginTransaction().apply {
            when (to) {
                Navigable.Destination.List -> replace(
                    R.id.container,
                    listFragment,
                    listFragment.javaClass.name
                )
                Navigable.Destination.Add -> {
                    replace(
                        R.id.container,
                        EditFragment(),
                        EditFragment::class.java.name
                    )
                    addToBackStack(EditFragment::class.java.name) // funkcja back
                }
            }
        }.commit()
    }


}
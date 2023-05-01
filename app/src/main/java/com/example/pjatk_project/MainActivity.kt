package com.example.pjatk_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatk_project.databinding.ActivityMainBinding
import com.example.pjatk_project.fragments.ARG_EDIT_ID
import com.example.pjatk_project.fragments.EditFragment
import com.example.pjatk_project.fragments.ListFragment

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
                R.id.container,
                listFragment,
                listFragment.javaClass.name //tag, po którym będzie można łatwo znaleźć w fragmetManagerze
            )
            .commit() // wpięcie do managera
    }

    override fun navigate(to: Navigable.Destination, id: Long?) {
        supportFragmentManager.beginTransaction().apply {
            when (to) {
                Navigable.Destination.List -> {
                    replace(
                        R.id.container,
                        listFragment,
                        listFragment.javaClass.name
                    )
                }
                Navigable.Destination.Add -> {
                    replace(
                        R.id.container,
                        EditFragment(),
                        EditFragment::class.java.name
                    )
                    addToBackStack(EditFragment::class.java.name) // funkcja back
                }
                Navigable.Destination.Edit -> {
                    replace(
                        R.id.container,
                        EditFragment::class.java, // podanie klasy
                        Bundle().apply {// podanie argumentów klasy
                            putLong(
                                ARG_EDIT_ID, // id edycji
                                id ?: -1L // jeśli nie ma id, to ustaw na -1
                                // (-1 to również oznacza, że jest się w Add, a nie w Edit, więc jest doublecheck)
                            )
                        },
                        EditFragment::class.java.name
                    )
                    addToBackStack(EditFragment::class.java.name) // funkcja back
                }
            }
        }.commit()
    }


}
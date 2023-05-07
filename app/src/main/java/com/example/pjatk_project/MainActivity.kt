package com.example.pjatk_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.pjatk_project.databinding.ActivityMainBinding
import com.example.pjatk_project.fragments.ARG_ADD_ID
import com.example.pjatk_project.fragments.ARG_DETAILS_ID
import com.example.pjatk_project.fragments.ARG_EDIT_ID
import com.example.pjatk_project.fragments.DetailsFragment
import com.example.pjatk_project.fragments.EditFragment
import com.example.pjatk_project.fragments.ListFragment

class MainActivity : AppCompatActivity(), Navigable {

    private lateinit var listFragment: ListFragment

    // stworzenie aktywności
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root) // view binding
        addListFragmentToFragmentManager()
    }

    override fun navigate(destination: Navigable.Destination, id: Long?) {
        supportFragmentManager.beginTransaction().apply {
            when (destination) {
                Navigable.Destination.List -> {
                    replaceWithListFragment(this)
                }

                Navigable.Destination.Add -> {
                    replaceWithEditFragment(this, ARG_ADD_ID, id)
                }

                Navigable.Destination.Edit -> {
                    replaceWithEditFragment(this, ARG_EDIT_ID, id)
                }

                Navigable.Destination.Details -> {
                    replaceWithDetailsFragment(this, ARG_DETAILS_ID, id)
                }
            }
        }.commit()
    }

    private fun replaceWithListFragment(destination: FragmentTransaction) {
        destination.replace(
            R.id.container,
            listFragment,
            listFragment.javaClass.name
        )
    }

    private fun replaceWithEditFragment(
        destination: FragmentTransaction,
        viewId: String,
        id: Long?
    ) {
        destination.replace(
            R.id.container,
            EditFragment::class.java,
            Bundle().apply {// podanie argumentów klasy
                putLong(
                    viewId, // id edycji/dodawania
                    id ?: -1L // jeśli nie ma id, to ustaw na -1
                )
            },
            EditFragment::class.java.name
        )
        // dodanie do stacku (funkcja back)
        destination.addToBackStack(EditFragment::class.java.name)
    }

    private fun replaceWithDetailsFragment(
        destination: FragmentTransaction,
        viewId: String,
        id: Long?
    ) {
        destination.replace(
            R.id.container,
            DetailsFragment::class.java,
            Bundle().apply {// podanie argumentów klasy
                putLong(
                    viewId, // id szczegółów
                    id ?: -1L // jeśli nie ma id, to ustaw na -1
                )
            },
            DetailsFragment::class.java.name
        )
        // dodanie do stacku (funkcja back)
        destination.addToBackStack(DetailsFragment::class.java.name)
    }

    private fun addListFragmentToFragmentManager() {
        listFragment = ListFragment()
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                listFragment,
                listFragment.javaClass.name //tag do fragmentManagera
            )
            .commit() // wpięcie do managera
    }
}
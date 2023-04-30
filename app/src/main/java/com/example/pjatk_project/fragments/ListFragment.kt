package com.example.pjatk_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.data.DataSource
import com.example.pjatk_project.DishesAdapter
import com.example.pjatk_project.Navigable
import com.example.pjatk_project.data.DishDatabase
import com.example.pjatk_project.databinding.FragmentListBinding
import com.example.pjatk_project.model.Dish
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var adapter: DishesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DishesAdapter()
        loadData()


        // podpięcie listy z danymi -> do widoku listy
        binding.list.let {
            it.adapter = adapter // podpięcie adaptera do ListFragment
            it.layoutManager =
                LinearLayoutManager(requireContext()) // ustalenie layoutu dla dodawania elementów
            // requireContext() zwraca kontekst dla tego fragmentu (sam fragment nie posiada kontekstu)
        }

        binding.btAdd.setOnClickListener {
            (activity as? Navigable)?.navigate(Navigable.Destination.Add) // ? - sprawdzanie czy
            // dane activity implementuje interfejs Navigable (jeśli nie, to null).
            // Jeśli tak, to użyte zostaje navigate()
        }
    }

    // oddzielny wątek na dostęp do bazy danych, żeby nie zajmować wątku głównego (UI)
    fun loadData() = thread {
        val dishes = DishDatabase.open(
            requireContext() //requireContext() - żeby sięgnąć po kontekst aktywności, a nie fragmentu
        ).dishes.getAll().map { entity -> // mapowanie encji na obiekt Dish
            Dish(
                entity.name,
                entity.ingredients.split("\n"),
                resources.getIdentifier( // pobranie identyfikatora zasobu
                    entity.icon,
                    "drawable",
                    requireContext().packageName
                )
            )
        }
        adapter?.replace(dishes) // dodanie danych pobranych z bazy danych do adaptera
    }


    override fun onStart() {
        super.onStart()
        // ponowne użycie w onStart(), bo onViewCreated może się wykonać wcześniej,
        // kiedy adapter nie został jeszcze zainicjalizowany
        adapter?.replace(DataSource.dishes)
        loadData() //podwójne wywołanie loadData() - tu i w onViewCreated()
    }

}
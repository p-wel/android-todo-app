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
import com.example.pjatk_project.databinding.FragmentListBinding

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

        adapter = DishesAdapter().apply { // użycie metod na adapterze
            replace(DataSource.dishes) // użycie metody do dodania danych do adaptera (w widoku)
        }

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

    override fun onStart() {
        super.onStart()
        // ponowne użycie w onStart(), bo onViewCreated może się wykonać wcześniej,
        // kiedy adapter nie został jeszcze zainicjalizowany
        adapter?.replace(DataSource.dishes)
    }

}
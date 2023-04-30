package com.example.pjatk_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.databinding.FragmentListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: DishesAdapter

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
            replace(DataSource.dishes) // użycie metody do dodania danych do adaptera
        }

        // podpięcie listy z danymi -> do widoku listy
        binding.list.let {
            it.adapter = adapter // podpięcie adaptera do ListFragment
            it.layoutManager =
                LinearLayoutManager(requireContext()) // ustalenie layoutu dla dodawania elementów
            // requireContext() zwraca kontekst dla tego fragmentu (sam fragment nie posiada kontekstu)

        }


    }

}
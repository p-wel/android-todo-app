package com.example.pjatk_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.DishesAdapter
import com.example.pjatk_project.Navigable
import com.example.pjatk_project.adapters.SwipeToRemove
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
    private lateinit var db: DishDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //requireContext() - żeby sięgnąć po kontekst aktywności, a nie kontekst fragmentu
        db = DishDatabase.open(requireContext())
    }

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

        adapter = DishesAdapter().apply {
            // podpięcie onItemClick od razu przy tworzeniu adaptera
            onItemClick = {
                (activity as? Navigable)?.navigate(Navigable.Destination.Edit, it)
            }
        }

        loadData()

        // podpięcie listy z danymi -> do widoku listy
        binding.list.let {
            // it. oznacza tu indeks elementu na liście
            it.adapter = adapter // podpięcie adaptera do ListFragment
            it.layoutManager =
                LinearLayoutManager(requireContext()) // ustalenie layoutu dla dodawania elementów

            // podpięcie touchHelpera
            ItemTouchHelper(
                SwipeToRemove {
                    // sprawdzenie, czy istnieje item do usunięcia
                    adapter?.removeItem(it)?.let {
                        // oddzielny wątek na usunięcie z bazy
                        thread {
                            db.dishes.remove(it.id) // usunięcie z bazy
                        }
                    }
                }
            ).attachToRecyclerView(it)
        }

        binding.btAdd.setOnClickListener {
            // "?" to sprawdzanie czy dane activity implementuje interfejs Navigable:
            //      jeśli tak, to użyte zostaje navigate()
            //      jeśli nie, to null
            (activity as? Navigable)?.navigate(Navigable.Destination.Add)
        }

        binding.btSort.setOnClickListener {
            adapter?.sort()
        }
    }

    // oddzielny wątek na dostęp do bazy danych, żeby nie zajmować wątku głównego (UI)
    fun loadData() = thread {
        val dishes = db.dishes.getAll().map { entity -> // mapowanie encji na obiekt Dish
            Dish(
                entity.id,
                entity.name,
                entity.ingredients.split("\n"),
                resources.getIdentifier( // pobranie identyfikatora zasobu
                    entity.icon,
                    "drawable",
                    requireContext().packageName
                )
            )
        }
        // w wątku głównym UI
        requireActivity().runOnUiThread {
            adapter?.replace(dishes) // dodanie danych pobranych z bazy danych do adaptera
        }
    }

    override fun onStart() {
        super.onStart()
        loadData() //podwójne wywołanie loadData() - tu i w onViewCreated()
    }

    // zamknięcie dostępu do bazy
    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}
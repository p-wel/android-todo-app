package com.example.pjatk_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.adapters.DishImagesAdapter
import com.example.pjatk_project.data.DishDatabase
import com.example.pjatk_project.data.model.DishEntity
import com.example.pjatk_project.databinding.FragmentEditBinding
import kotlin.concurrent.thread

// id tego fragmentu (przydatne przy przekazywaniu argumentów podczas nawigacji)
const val ARG_EDIT_ID = "edit_id"
const val ARG_ADD_ID = "add_id"

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var adapter: DishImagesAdapter
    private lateinit var db: DishDatabase
    private var dish: DishEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // dostęp do bazy
        db = DishDatabase.open(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DishImagesAdapter()

        // pobranie id widoku
        val id = requireArguments().getLong(
            ARG_EDIT_ID, // [edycja]
            -1 // [dodawanie]
        )

        if (id != -1L) {
            // [edycja]
            thread { //dostęp do bazy w oddzielnym wątku (nie można blokować głównego wątku UI!)
                dish = db.dishes.getDish(id) // pobranie odpowiedniej encji z bazy

                // przypisanie danych w innym wątku - na głównym wątku UI
                requireActivity().runOnUiThread {
                    // wypisanie danych aktualnego elementu w formularzu edycji (lub brak, jeśli używa się dodawania)
                    binding.dishName.setText(dish?.name ?: "")
                    binding.ingredients.setText(dish?.ingredients ?: "")

                    // ustawienie selecta na odpowiedniej ikonie [edycja]
                    adapter.setSelection(
                        dish?.icon.let { //let{} żeby nie przekazać tu przypadkiem nulla
                            resources.getIdentifier(it, "drawable", requireContext().packageName)
                        }
                    )
                }
            }
        }

        // użycie metod na RecyclerView images
        binding.images.apply {
            // wewnątrz "apply{}":
            //      - this jest obiektem RecyclerView
            //      - this@EditFragment jest obiektem, którego adapter ma zostać przypisany
            adapter = this@EditFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // save wykonujący edycję/dodawanie elementów
        binding.save.setOnClickListener {
            val dish = dish?.copy( // jeśli istnieje dish, to stwórz jego kopię
                // [edycja]
                name = binding.dishName.text.toString(),
                ingredients = binding.ingredients.text.toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes) // pobranie ikony z aktualnego elementu
            ) ?: DishEntity( // jeśli nie istnieje dish, to dodaj nowy obiekt
                // [dodawanie]
                name = binding.dishName.text.toString(),
                ingredients = binding.ingredients.text.toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes) // pobranie ikony z aktualnego elementu
            )
            this.dish =
                dish // dla pewności, przypisanie zaktualizowanego elementu (do ew. późniejszego użytku)

            // osobny wątek na dodanie nowego obiektu
            thread {
                db.dishes.addDish(dish) // dodanie nowego Dish do bazy
                parentFragmentManager.popBackStack()
            }
        }
    }

    // zamknięcie dostępu do bazy
    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }


}
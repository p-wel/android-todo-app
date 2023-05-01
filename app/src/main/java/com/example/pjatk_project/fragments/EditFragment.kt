package com.example.pjatk_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.Navigable
import com.example.pjatk_project.adapters.DishImagesAdapter
import com.example.pjatk_project.data.DishDatabase
import com.example.pjatk_project.data.model.DishEntity
import com.example.pjatk_project.databinding.FragmentEditBinding
import kotlin.concurrent.thread

const val ARG_EDIT_ID = "edit_id"

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
        val id = requireArguments().getLong(
            ARG_EDIT_ID, // przypisanie odpowiedniego id DishEntity do edycji
            -1 // default -1, jeśli nie uda się pobrać
        )

        // dostęp do bazy
        db = DishDatabase.open(requireContext())
        if (id != -1L) {
            dish = db.dishes.getDish(id) // pobranie z bazy danych po id
        }
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

        // wypisanie danych aktualnego elementu w formularzu edycji (lub brak, jeśli używa się dodawania)
        binding.dishName.setText(dish?.name ?: "")
        binding.ingredients.setText(dish?.ingredients ?: "")
        binding.ingredients.setText(dish?.ingredients ?: "")

        // stworzenie adaptera dla image i ustawienie selecta na odpowiedniej ikonie [edycja]
        adapter = DishImagesAdapter()
        adapter.setSelection(dish?.icon.let {
            resources.getIdentifier(
                it,
                "drawable",
                requireContext().packageName
            )
        }
        ) //let{} żeby nie przekazać tu przypadkiem nulla

        binding.images.apply { // użycie metod na RecyclerView images
            // wewnątrz apply{}
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
                (activity as? Navigable)?.navigate(Navigable.Destination.List)
            }
        }
    }


}
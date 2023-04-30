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

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var adapter: DishImagesAdapter

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

        binding.images.apply { // użycie metod na RecyclerView images
            // wewnątrz apply{}
            //      - this jest obiektem RecyclerView
            //      - this@EditFragment jest obiektem, którego adapter ma zostać przypisany
            adapter = this@EditFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.save.setOnClickListener {
            val newDish = DishEntity(
                name = binding.dishName.text.toString(),
                ingredients = binding.ingredients.text.toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes) // pobranie ikony z aktualnego elementu
            )

            // osobny wątek na dostęp do bazy i dodanie nowego obiektu
            thread {
                DishDatabase.open(requireContext()).dishes.addDish(newDish) // dodanie nowego Dish do bazy
                (activity as? Navigable)?.navigate(Navigable.Destination.List)
            }
        }
    }


}
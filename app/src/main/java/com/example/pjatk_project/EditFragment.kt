package com.example.pjatk_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.databinding.FragmentEditBinding

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
            val newDish = Dish(
                binding.dishName.text.toString(),
                binding.ingredients.text.toString().split("\n"),
                adapter.selectedIdRes
            )
            DataSource.dishes.add(newDish)
            (activity as? Navigable)?.navigate(Navigable.Destination.List)
        }
    }


}
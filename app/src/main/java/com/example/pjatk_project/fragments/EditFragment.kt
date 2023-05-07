package com.example.pjatk_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.adapters.TaskImagesAdapter
import com.example.pjatk_project.data.TaskDatabase
import com.example.pjatk_project.data.model.TaskEntity
import com.example.pjatk_project.databinding.FragmentEditBinding
import kotlin.concurrent.thread

// id tego fragmentu (przydatne przy przekazywaniu argumentów podczas nawigacji)
const val ARG_EDIT_ID = "edit_id"
const val ARG_ADD_ID = "add_id"
const val ARG_DETAILS_ID = "details_id"

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var adapter: TaskImagesAdapter
    private lateinit var db: TaskDatabase
    private var task: TaskEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // dostęp do bazy
        db = TaskDatabase.open(requireContext())
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

        adapter = TaskImagesAdapter()

        // pobranie id widoku
        val id = requireArguments().getLong(
            ARG_EDIT_ID, // [edycja]
            -1 // [dodawanie]
        )

        if (id != -1L) {
            // [edycja]
            thread { //dostęp do bazy w oddzielnym wątku (nie można blokować głównego wątku UI!)
                task = db.tasks.getTask(id) // pobranie odpowiedniej encji z bazy

                // przypisanie danych w innym wątku - na głównym wątku UI
                requireActivity().runOnUiThread {
                    // wypisanie danych aktualnego elementu w formularzu edycji (lub brak, jeśli używa się dodawania)
                    binding.taskName.setText(task?.name ?: "")
                    binding.description.setText(task?.description ?: "")

                    // ustawienie selecta na odpowiedniej ikonie [edycja]
                    adapter.setSelection(
                        task?.icon.let { //let{} żeby nie przekazać tu przypadkiem nulla
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
        binding.buttonSave.setOnClickListener {
            val task = task?.copy( // jeśli istnieje obiekt, to stwórz jego kopię
                // [edycja]
                name = binding.taskName.text.toString(),
                description = binding.description.text.toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes) // pobranie ikony z aktualnego elementu
            ) ?: TaskEntity( // jeśli nie istnieje obiekt, to dodaj nowy
                // [dodawanie]
                name = binding.taskName.text.toString(),
                description = binding.description.text.toString(),
                icon = resources.getResourceEntryName(adapter.selectedIdRes) // pobranie ikony z aktualnego elementu
            )
            this.task =
                task // dla pewności, przypisanie zaktualizowanego elementu (do ew. późniejszego użytku)

            // osobny wątek na dodanie nowego obiektu
            thread {
                db.tasks.addTask(task) // dodanie nowego obiektu do bazy
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
package com.example.pjatk_project.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatk_project.Navigable
import com.example.pjatk_project.adapters.TaskImagesAdapter
import com.example.pjatk_project.data.TaskDatabase
import com.example.pjatk_project.data.model.TaskEntity
import com.example.pjatk_project.databinding.FragmentDetailsBinding
import kotlin.concurrent.thread

const val ARG_DETAILS_ID = "details_id"

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var adapter: TaskImagesAdapter
    private lateinit var db: TaskDatabase
    private var task: TaskEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = TaskDatabase.open(requireContext()) // dostęp do bazy
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TaskImagesAdapter()
        val viewId = getViewId()

        if (viewId != -1L) {
            putDataIntoFragment(viewId)
        }
        putImagesIntoFragment()
        setListeners()
    }

    private fun getViewId(): Long {
        return requireArguments().getLong(
            ARG_DETAILS_ID, // [szczegóły]
            -1 // [default]
        )
    }

    private fun putDataIntoFragment(viewId: Long) {
        thread { //dostęp do bazy w oddzielnym wątku (nie można blokować głównego wątku UI!)
            task = db.tasks.getTask(viewId) // pobranie odpowiedniej encji z bazy

            // przypisanie danych w innym wątku - na głównym wątku UI
            requireActivity().runOnUiThread {
                // wypisanie danych aktualnego elementu w polach taska
                binding.taskName.setText(task?.name ?: "")
                binding.description.setText(task?.description ?: "")

                // ustawienie selecta na odpowiedniej ikonie [szczegóły]
                adapter.setSelection(
                    task?.icon.let { //let{} żeby nie przekazać tu przypadkiem nulla
                        resources.getIdentifier(it, "drawable", requireContext().packageName)
                    }
                )
            }
        }
    }

    private fun putImagesIntoFragment() {
        binding.images.apply {
            // wewnątrz "apply{}":
            //      - this jest obiektem RecyclerView
            //      - this@DetailsFragment jest obiektem, którego adapter ma zostać przypisany
            adapter = this@DetailsFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setListeners() {
        binding.buttonEdit.setOnClickListener {
            val id = requireArguments().getLong(ARG_DETAILS_ID)
            parentFragmentManager.popBackStack()
            (activity as? Navigable)?.navigate(Navigable.Destination.Edit, id)
        }

        binding.buttonShare.setOnClickListener {
            val message = task?.name + "\n" + task?.description
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra("Share this", message)

            val chooser = Intent.createChooser(intent, "Share using...")
            startActivity(chooser)
        }
    }
}

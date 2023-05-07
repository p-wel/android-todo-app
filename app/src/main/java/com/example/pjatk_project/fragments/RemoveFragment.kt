package com.example.pjatk_project.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class RemoveFragment(private val listFragment: ListFragment) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        return AlertDialog.Builder(requireContext())
            .setTitle("Remove")
            .setMessage("Remove the task?")
            .setPositiveButton("Remove") { _: DialogInterface, _: Int ->
                listFragment.invokeItemRemoval()
            }
            .setNegativeButton("Back") { _: DialogInterface, _: Int ->
                dismiss()
            }
            .create()
    }
}
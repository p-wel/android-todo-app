package com.example.android_app.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app.databinding.TaskImageBinding

// holder na obrazki
class TaskImageViewHolder(private val binding: TaskImageBinding) : RecyclerView.ViewHolder(binding.root) {

    // bindowanie obrazka o danym resId
    fun bind(resId: Int, isSelected: Boolean) {
        binding.image.setImageResource(resId)
        binding.selectedFrame.visibility =
            if (isSelected) View.VISIBLE else View.INVISIBLE
    }
}
package com.example.pjatk_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val icon: String // iconId mogłoby się zmienić w trakcie kompilacji, więc używam nazwy icon
)

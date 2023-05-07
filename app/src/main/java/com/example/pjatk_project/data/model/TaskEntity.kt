package com.example.pjatk_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var description: String,
    var icon: String // iconId mogłoby się zmienić w trakcie kompilacji, więc używam nazwy icon
)

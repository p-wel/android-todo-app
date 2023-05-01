package com.example.pjatk_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pjatk_project.data.model.TaskEntity

// klasa odpowiadająca za instancję bazy danych
@Database(
    entities = [TaskEntity::class], //@Database przyjmuje tablicę klas .kt
    version = 1 // wersja bazy danych
)
abstract class TaskDatabase : RoomDatabase() {
    abstract val tasks: TaskDao

    // dostęp do bazy danych tworzonej przez Room Builder
    // companion object - nie potrzebuje instancji klasy (podobnie do obiektu statycznego)
    companion object {
        fun open(context: Context): TaskDatabase =
            Room.databaseBuilder(
                context, TaskDatabase::class.java, "tasks.db"
            ).build()
    }
}
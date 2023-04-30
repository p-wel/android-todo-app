package com.example.pjatk_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pjatk_project.data.model.DishEntity

// klasa odpowiadająca za instancję bazy danych
@Database(
    entities = [DishEntity::class], //@Database przyjmuje tablicę klas .kt
    version = 1 // wersja bazy danych
)
abstract class DishDatabase : RoomDatabase() {
    abstract val dishes: DishDao

    // companion object - nie potrzebuje instancji klasy (podobnie do obiektu statycznego)
    companion object {
        // dostęp do bazy danych DishDatabase - stworzonej przez Room Builder
        fun open(context: Context): DishDatabase =
            Room.databaseBuilder(
                context, DishDatabase::class.java, "dishes.db"
            ).build()
    }
}
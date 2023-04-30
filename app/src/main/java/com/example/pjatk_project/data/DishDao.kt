package com.example.pjatk_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pjatk_project.data.model.DishEntity

// interfejs dla dostępu do danych i ich edycji
@Dao
interface DishDao {
    @Query("SELECT * FROM dish;")
    fun getAll(): List<DishEntity>

    @Insert
    fun addDish(newDish: DishEntity)

    @Update
    fun updateDish(newDish: DishEntity)
}
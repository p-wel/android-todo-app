package com.example.pjatk_project.data

import androidx.room.*
import com.example.pjatk_project.data.model.DishEntity

// interfejs dla dostępu do danych i ich edycji
@Dao
interface DishDao {
    @Query("SELECT * FROM dish;")
    fun getAll(): List<DishEntity>

    @Query("SELECT * FROM dish WHERE id = :id;")
    fun getDish(id: Long): DishEntity

    // ustawienie OnConflictStrategy
    // taki konflikt powstanie, bo używamy tego samego formularza do Insert/Update [edycja/dodawanie]
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDish(newDish: DishEntity)

    @Update
    fun updateDish(newDish: DishEntity)

    // Query, bo usunięcie jest wg id Disha, a nie bezpośrednio na encji
    @Query("DELETE FROM dish WHERE id = :id;")
    fun remove(id: Long)

}
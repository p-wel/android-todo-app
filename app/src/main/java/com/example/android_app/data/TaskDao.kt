package com.example.android_app.data

import androidx.room.*
import com.example.android_app.data.model.TaskEntity

// interfejs dla dostępu do danych i ich edycji
@Dao
interface TaskDao {
    @Query("SELECT * FROM task;")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :id;")
    fun getTask(id: Long): TaskEntity

    // ustawienie OnConflictStrategy
    // taki konflikt powstanie, bo użyty jest ten sam formularz do Dodaj/Edytuj
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(newTask: TaskEntity)

    @Update
    fun updateTask(newTask: TaskEntity)

    // Query, bo usunięcie jest wg id elementu, a nie bezpośrednio na encji
    @Query("DELETE FROM task WHERE id = :id;")
    fun remove(id: Long)

}
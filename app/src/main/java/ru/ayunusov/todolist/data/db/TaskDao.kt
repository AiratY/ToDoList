package ru.ayunusov.todolist.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.ayunusov.todolist.domain.model.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE dateStart BETWEEN :start AND :end ORDER BY dateStart ASC")
    fun loadTasks(start: Long, end: Long): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id == :id")
    fun getTask(id: Int): Flow<Task>
}
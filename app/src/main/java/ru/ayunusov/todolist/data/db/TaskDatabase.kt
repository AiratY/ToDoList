package ru.ayunusov.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ayunusov.todolist.domain.model.Task

@Database(entities = [Task::class], version = VERSION_DB)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}

private const val VERSION_DB = 1
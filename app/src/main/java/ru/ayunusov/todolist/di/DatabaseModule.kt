package ru.ayunusov.todolist.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ayunusov.todolist.data.db.TaskDao
import ru.ayunusov.todolist.data.db.TaskDatabase
import ru.ayunusov.todolist.other.NAME_DB

@Module
class DatabaseModule {
    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao {
        return db.taskDao()
    }

    @Provides
    fun provideDB(context: Context): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, NAME_DB).build()
    }
}
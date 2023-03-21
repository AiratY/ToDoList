package ru.ayunusov.todolist.di

import dagger.Binds
import dagger.Module
import ru.ayunusov.todolist.data.repository.TaskRepository
import ru.ayunusov.todolist.domain.ITaskRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideTaskRepository(taskRepository: TaskRepository):ITaskRepository
}
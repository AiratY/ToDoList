package ru.ayunusov.todolist.di

import dagger.Binds
import dagger.Module
import ru.ayunusov.todolist.domain.IAddTaskInteractor
import ru.ayunusov.todolist.domain.IGetListTasksInteractor
import ru.ayunusov.todolist.domain.ITaskInteractor
import ru.ayunusov.todolist.domain.TaskInteractor
import ru.ayunusov.todolist.domain.IDeleteTaskInteractor
import javax.inject.Singleton

@Module
abstract class InteractorModule {
    @Singleton
    @Binds
    abstract fun provideAddTaskInteractor(taskInteractor: TaskInteractor): IAddTaskInteractor

    @Singleton
    @Binds
    abstract  fun provideGetListInteractor(taskInteractor: TaskInteractor): IGetListTasksInteractor

    @Singleton
    @Binds
    abstract  fun provideTaskInteractor(taskInteractor: TaskInteractor): ITaskInteractor

    @Singleton
    @Binds
    abstract  fun provideDeleteTaskInteractor(taskInteractor: TaskInteractor): IDeleteTaskInteractor
}
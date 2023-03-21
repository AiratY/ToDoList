package ru.ayunusov.todolist.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.ayunusov.todolist.other.TodoViewModelFactory

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: TodoViewModelFactory
    ): ViewModelProvider.Factory
}
package ru.ayunusov.todolist.presentation.todolist.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ayunusov.todolist.di.ViewModelKey
import ru.ayunusov.todolist.presentation.todolist.ToDoViewModel

@Module
abstract class ToDoModule {
    @Binds
    @IntoMap
    @ViewModelKey(ToDoViewModel::class)
    abstract fun bindViewModel(viewModel: ToDoViewModel): ViewModel
}
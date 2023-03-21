package ru.ayunusov.todolist.presentation.add.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ayunusov.todolist.di.ViewModelKey
import ru.ayunusov.todolist.presentation.add.AddTaskViewModel

@Module
abstract class AddTaskModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddTaskViewModel::class)
    abstract fun provideAddTaskModel(addTaskViewModel: AddTaskViewModel): ViewModel
}
package ru.ayunusov.todolist.presentation.todolist.di

import dagger.Subcomponent
import ru.ayunusov.todolist.di.FragmentScope
import ru.ayunusov.todolist.presentation.todolist.ToDoFragment

@FragmentScope
@Subcomponent(modules = [ToDoModule::class])
interface TodoListComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): TodoListComponent
    }

    fun inject(toDoFragment: ToDoFragment)
}
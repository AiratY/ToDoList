package ru.ayunusov.todolist.presentation.add.di

import dagger.Subcomponent
import ru.ayunusov.todolist.di.FragmentScope
import ru.ayunusov.todolist.presentation.add.AddTaskFragment

@FragmentScope
@Subcomponent(modules = [AddTaskModule::class])
interface AddTaskComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AddTaskComponent
    }

    fun inject(addTaskFragment: AddTaskFragment)
}
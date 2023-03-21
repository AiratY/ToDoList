package ru.ayunusov.todolist.presentation.main.di

import dagger.Subcomponent
import ru.ayunusov.todolist.di.ActivityScope
import ru.ayunusov.todolist.presentation.add.di.AddTaskComponent
import ru.ayunusov.todolist.presentation.description.di.DescComponent
import ru.ayunusov.todolist.presentation.main.MainActivity
import ru.ayunusov.todolist.presentation.todolist.di.TodoListComponent

@ActivityScope
@Subcomponent(modules = [ActivitySubComponents::class])
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun addTaskComponent(): AddTaskComponent.Factory
    fun todoListComponent(): TodoListComponent.Factory
    fun descComponent(): DescComponent.Factory

    fun inject(mainActivity: MainActivity)
}
package ru.ayunusov.todolist.presentation.main.di

import dagger.Module
import ru.ayunusov.todolist.presentation.add.di.AddTaskComponent
import ru.ayunusov.todolist.presentation.description.di.DescComponent
import ru.ayunusov.todolist.presentation.todolist.di.TodoListComponent

@Module(subcomponents = [AddTaskComponent::class, TodoListComponent::class, DescComponent::class])
class ActivitySubComponents
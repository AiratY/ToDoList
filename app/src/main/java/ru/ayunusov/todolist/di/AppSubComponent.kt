package ru.ayunusov.todolist.di

import dagger.Module
import ru.ayunusov.todolist.presentation.main.di.ActivityComponent

@Module(subcomponents = [ActivityComponent::class])
class AppSubComponent
package ru.ayunusov.todolist.presentation.description.di

import dagger.Subcomponent
import ru.ayunusov.todolist.di.FragmentScope
import ru.ayunusov.todolist.presentation.description.DescriptionDoFragment

@FragmentScope
@Subcomponent(modules = [DescModule::class])
interface DescComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DescComponent
    }

    fun inject(descriptionDoFragment: DescriptionDoFragment)
}
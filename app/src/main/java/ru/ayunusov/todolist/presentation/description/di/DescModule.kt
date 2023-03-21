package ru.ayunusov.todolist.presentation.description.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.ayunusov.todolist.di.ViewModelKey
import ru.ayunusov.todolist.presentation.description.DescViewModel

@Module
abstract class DescModule {
    @Binds
    @IntoMap
    @ViewModelKey(DescViewModel::class)
    abstract fun bindViewModel(viewModel: DescViewModel): ViewModel
}
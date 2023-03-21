package ru.ayunusov.todolist.di

import dagger.Component
import ru.ayunusov.todolist.App
import ru.ayunusov.todolist.presentation.main.di.ActivityComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppSubComponent::class,
        AppModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        InteractorModule::class,
        ViewModelBuilderModule::class,
        DispatcherModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)
    fun activityComponent(): ActivityComponent.Factory
}
package ru.ayunusov.todolist

import android.app.Application
import ru.ayunusov.todolist.di.AppComponent
import ru.ayunusov.todolist.di.AppModule
import ru.ayunusov.todolist.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
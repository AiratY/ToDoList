package ru.ayunusov.todolist.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideContext(): Context = context.applicationContext

    @Singleton
    @Provides
    fun provideResources(): Resources = context.resources
}
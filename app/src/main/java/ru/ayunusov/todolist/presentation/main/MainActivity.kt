package ru.ayunusov.todolist.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ayunusov.todolist.App
import ru.ayunusov.todolist.R
import ru.ayunusov.todolist.presentation.main.di.ActivityComponent

class MainActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = (applicationContext as App).appComponent.activityComponent().create()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
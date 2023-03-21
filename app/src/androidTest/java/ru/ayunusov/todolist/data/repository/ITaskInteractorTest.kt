package ru.ayunusov.todolist.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.ayunusov.todolist.domain.model.Task

class ITaskInteractorTest(private val task: Task): ru.ayunusov.todolist.domain.ITaskInteractor {
    override fun getTask(id: Int): Flow<Task> {
        return flowOf(task)
    }
}
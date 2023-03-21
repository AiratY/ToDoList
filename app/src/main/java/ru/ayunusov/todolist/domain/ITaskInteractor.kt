package ru.ayunusov.todolist.domain

import kotlinx.coroutines.flow.Flow
import ru.ayunusov.todolist.domain.model.Task

interface ITaskInteractor {
    /**
     * Возвращает задчу по id
     * @param id
     * @return task
     * */
    fun getTask(id: Int): Flow<Task>
}
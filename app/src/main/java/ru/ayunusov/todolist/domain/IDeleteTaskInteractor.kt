package ru.ayunusov.todolist.domain

import ru.ayunusov.todolist.domain.model.Task

interface IDeleteTaskInteractor {
    /**
     * Удаляет задачу
     * */
    suspend fun deleteTask(task: Task)
}
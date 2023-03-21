package ru.ayunusov.todolist.domain

import ru.ayunusov.todolist.domain.model.Task

interface IAddTaskInteractor {
    /**
     * Добавляет новую задачу
     * @param task - новая задача
     * */
    suspend fun addNewTask(task: Task)
}
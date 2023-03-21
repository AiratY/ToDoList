package ru.ayunusov.todolist.domain

import kotlinx.coroutines.flow.Flow
import ru.ayunusov.todolist.domain.model.Task

interface ITaskRepository {
    /**
     * Добавление новой задачи
     * */
    suspend fun addTask(task: Task)

    /**
     * Возвращает список задач в указанном диапазоне
     * @param start - начало диапазона
     * @param end - конец диапазона
     * @return список задач
     * */
    fun getListTask(start: Long, end: Long): Flow<List<Task>>

    /**
     * Возвращает задчу по id
     * @param id
     * @return task
     * */
    fun getTask(id: Int): Flow<Task>

    /**
     * Удаляет задачу
     * */
    suspend fun deleteTask(task: Task)
}
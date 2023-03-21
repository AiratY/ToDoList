package ru.ayunusov.todolist.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.ayunusov.todolist.data.db.TaskDao
import ru.ayunusov.todolist.di.IoDispatcher
import ru.ayunusov.todolist.domain.ITaskRepository
import ru.ayunusov.todolist.domain.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    @IoDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ITaskRepository {
    override suspend fun addTask(task: Task) = withContext(defaultDispatcher) {
        taskDao.insertTask(task)
    }

    override fun getListTask(start: Long, end: Long): Flow<List<Task>> {
        return taskDao.loadTasks(start, end)
    }

    override fun getTask(id: Int): Flow<Task> {
        return taskDao.getTask(id)
    }

    override suspend fun deleteTask(task: Task) = withContext(defaultDispatcher) {
        taskDao.deleteTask(task)
    }
}
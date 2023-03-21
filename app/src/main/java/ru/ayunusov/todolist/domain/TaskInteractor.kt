package ru.ayunusov.todolist.domain

import kotlinx.coroutines.flow.Flow
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.toLong
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskInteractor @Inject constructor(private val iTaskRepository: ITaskRepository) :
    IAddTaskInteractor, IGetListTasksInteractor, ITaskInteractor, IDeleteTaskInteractor {

    override suspend fun addNewTask(task: Task) {
        return iTaskRepository.addTask(task)
    }

    override fun getTasks(date: LocalDate): Flow<List<Task>> {
        val start = LocalDateTime.of(date, LocalTime.of(0, 0)).toLong()
        val end = LocalDateTime.of(date, LocalTime.of(23, 59)).toLong()
        return iTaskRepository.getListTask(start, end)
    }

    override fun getTask(id: Int): Flow<Task> {
        return iTaskRepository.getTask(id)
    }

    override suspend fun deleteTask(task: Task) {
        iTaskRepository.deleteTask(task)
    }
}
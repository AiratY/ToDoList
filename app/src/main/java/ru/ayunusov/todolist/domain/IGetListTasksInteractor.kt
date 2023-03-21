package ru.ayunusov.todolist.domain

import kotlinx.coroutines.flow.Flow
import ru.ayunusov.todolist.domain.model.Task
import java.time.LocalDate

interface IGetListTasksInteractor {
    /**
     * Возвращет список задач для указаннной даты
     * @param date - дата
     * @return - список задач
     * */
    fun getTasks(date: LocalDate): Flow<List<Task>>
}
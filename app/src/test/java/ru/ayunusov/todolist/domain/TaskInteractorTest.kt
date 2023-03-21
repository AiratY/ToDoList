package ru.ayunusov.todolist.domain

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.toLong
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class TaskInteractorTest {

    private val dateStart = LocalDateTime.of(2023, 2, 19, 20, 0)
    private val dateEnd = LocalDateTime.of(2023, 2, 19, 21, 0)

    private val task1 = Task(1, dateStart.toLong(), dateEnd.toLong(), "task1", "desc1")
    private val task2 = Task(2, 0L, 0L, "task2", "desc2")
    private val task3 = Task(3, 0L, 0L, "task3", "desc3")
    private val list = listOf(task1, task2, task3)

    @RelaxedMockK
    lateinit var iTaskRepository: ITaskRepository

    lateinit var taskInteractor: TaskInteractor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        taskInteractor = TaskInteractor(iTaskRepository)
    }

    @Test
    fun addNewTask() = runTest {
        taskInteractor.addNewTask(task1)
        verify { runBlocking { iTaskRepository.addTask(task1) } }
    }

    @Test
    fun getTasks() = runTest {
        every { taskInteractor.getTasks(dateStart.toLocalDate()) } returns flowOf(list)
        taskInteractor.getTasks(dateStart.toLocalDate()).collect {
            assertEquals(list, it)
        }
    }

    @Test
    fun getTask() = runTest {
        every { taskInteractor.getTask(task1.id) } returns flowOf(task1)
        taskInteractor.getTask(task1.id).collect {
            assertEquals(task1, it)
        }
    }

    @Test
    fun deleteTask() = runTest {
        taskInteractor.deleteTask(task2)
        verify { runBlocking { iTaskRepository.deleteTask(task2) } }
    }
}
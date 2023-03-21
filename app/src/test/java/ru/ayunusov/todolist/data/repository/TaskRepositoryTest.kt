package ru.ayunusov.todolist.data.repository

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.ayunusov.todolist.data.db.TaskDao
import ru.ayunusov.todolist.domain.model.Task

@ExperimentalCoroutinesApi
class TaskRepositoryTest {
    private val task1 = Task(1, 0L, 0L, "task1", "desc1")
    private val task2 = Task(2, 0L, 0L, "task2", "desc2")
    private val task3 = Task(3, 0L, 0L, "task3", "desc3")
    private val list = listOf(task1, task2, task3)

    @MockK
    lateinit var taskDao: TaskDao

    private lateinit var taskRepository: TaskRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        taskRepository = TaskRepository(taskDao, Dispatchers.Unconfined)
    }

    @Test
    fun addTask_() = runTest {
        val slot = slot<Task>()
        every { runBlocking { taskDao.insertTask(capture(slot)) } } just Runs

        taskRepository.addTask(task1)

        assertEquals(task1, slot.captured)
    }

    @Test
    fun deleteTask() = runTest {
        val slot = slot<Task>()
        every { runBlocking { taskDao.deleteTask(capture(slot)) } } just Runs

        taskRepository.deleteTask(task2)
        assertEquals(task2, slot.captured)
    }

    @Test
    fun getTask() = runTest {
        every { taskDao.getTask(task1.id) } returns flowOf(task1)

        taskRepository.getTask(task1.id).collect {
            assertEquals(it, task1)
        }
    }

    @Test
    fun getListTask() = runTest {
        every { taskDao.loadTasks(1L, 3L) } returns flowOf(list)
        taskRepository.getListTask(1L, 3L).collect {
            assertEquals(list, it)
        }
    }
}
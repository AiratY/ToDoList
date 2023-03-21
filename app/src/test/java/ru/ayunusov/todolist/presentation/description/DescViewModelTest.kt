package ru.ayunusov.todolist.presentation.description

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.ayunusov.todolist.domain.IDeleteTaskInteractor
import ru.ayunusov.todolist.domain.ITaskInteractor
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.getOrAwaitValue

class DescViewModelTest {

    @ExperimentalCoroutinesApi
    val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instanceExecuteRule = InstantTaskExecutorRule()

    @MockK
    lateinit var iTaskInteractor: ITaskInteractor

    @MockK
    lateinit var iDeleteTaskInteractor: IDeleteTaskInteractor

    private lateinit var descViewModel: DescViewModel

    private val task1 = Task(1, 0L, 0L, "task1", "desc1")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        descViewModel = DescViewModel(iTaskInteractor, iDeleteTaskInteractor)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }


    @Test
    fun setID() {
        every { iTaskInteractor.getTask(task1.id) } returns flowOf(task1)
        descViewModel.setID(task1.id)

        val value = descViewModel.task.getOrAwaitValue()
        assertEquals(task1, value)
    }

    @Test
    fun editTask_setToNavigateToEdit() {
        every { iTaskInteractor.getTask(task1.id) } returns flowOf(task1)
        descViewModel.setID(task1.id)

        descViewModel.editTask()
        val value = descViewModel.isNavigateToEdit.getOrAwaitValue()

        assertEquals(task1.id, value.getContentIfNotHandle())
    }

    @Test
    fun deleteTask() {
        descViewModel.deleteTask()

        val value = descViewModel.isNavigateUp.getOrAwaitValue()

        assertEquals(true, value.getContentIfNotHandle())
    }
}
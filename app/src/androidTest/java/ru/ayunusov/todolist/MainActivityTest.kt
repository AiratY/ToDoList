package ru.ayunusov.todolist

import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ayunusov.todolist.data.db.TaskDatabase
import ru.ayunusov.todolist.data.repository.TaskRepository
import ru.ayunusov.todolist.domain.ITaskRepository
import ru.ayunusov.todolist.domain.TaskInteractor
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.NAME_DB
import ru.ayunusov.todolist.other.toLong
import ru.ayunusov.todolist.presentation.main.MainActivity
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    private lateinit var repository: ITaskRepository
    private lateinit var interactor: TaskInteractor

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        val taskDao =
            Room.databaseBuilder(getApplicationContext(), TaskDatabase::class.java, NAME_DB).build()
                .taskDao()
        repository = TaskRepository(taskDao, UnconfinedTestDispatcher())
        interactor = TaskInteractor(repository)
    }

    @Test
    fun editTask() = runBlocking {
        interactor.addNewTask(task)

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withText(task.name)).perform(click())
        onView(withId(R.id.nameTaskTextView)).check(matches(withText(task.name)))
        onView(withId(R.id.dateTaskTextView)).check(matches(withText(task.getDate())))
        onView(withId(R.id.timeTaskTextView)).check(matches(withText("${task.getDateStartTime()} - ${task.getDateEndTime()}")))
        onView(withId(R.id.descTaskTextView)).check(matches(withText(task.description)))

        onView(withId(R.id.editTask)).perform(click())
        onView(withId(R.id.nameTaskEditText)).perform(replaceText("NEW TITLE"))
        onView(withId(R.id.descTaskEditText)).perform(replaceText("NEW DESCRIPTION"))
        onView(withId(R.id.addTask)).perform(click())

        onView(withText("NEW TITLE")).check(matches(isDisplayed()))
        onView(withText(task.name)).check(doesNotExist())

        activityScenario.close()
    }

    @Test
    fun deleteTask(): Unit = runBlocking {
        interactor.addNewTask(task2)

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withText(task2.name)).perform(click())
        onView(withId(R.id.nameTaskTextView)).check(matches(withText(task2.name)))
        onView(withId(R.id.dateTaskTextView)).check(matches(withText(task2.getDate())))
        onView(withId(R.id.timeTaskTextView)).check(matches(withText("${task2.getDateStartTime()} - ${task2.getDateEndTime()}")))
        onView(withId(R.id.descTaskTextView)).check(matches(withText(task2.description)))

        onView(withId(R.id.deleteTask)).perform(click())
        onView(withText(task2.name)).check(doesNotExist())

        activityScenario.close()
    }

    @Test
    fun goBack() = runBlocking {
        interactor.addNewTask(task3)

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withText(task3.name)).perform(click())
        onView(withId(R.id.nameTaskTextView)).check(matches(withText(task3.name)))
        onView(withId(R.id.dateTaskTextView)).check(matches(withText(task3.getDate())))
        onView(withId(R.id.timeTaskTextView)).check(matches(withText("${task3.getDateStartTime()} - ${task3.getDateEndTime()}")))
        onView(withId(R.id.descTaskTextView)).check(matches(withText(task3.description)))

        pressBack()

        onView(withText(task3.name)).check(matches(isDisplayed()))
        onView(withText(task3.description)).check(doesNotExist())

        activityScenario.close()
    }

    companion object {
        private val dateStart = LocalDateTime.now()
        private val dateEnd = LocalDateTime.now().plusHours(1L)
        private val task = Task(13, dateStart.toLong(), dateEnd.toLong(), "New Task", "Desc task")
        private val task2 = Task(17, dateStart.toLong(), dateEnd.toLong(), "Task2", "Desc task")
        private val task3 = Task(17, dateStart.toLong(), dateEnd.toLong(), "Task3", "Desc task3")
    }
}
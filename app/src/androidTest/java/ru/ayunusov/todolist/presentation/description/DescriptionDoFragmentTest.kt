package ru.ayunusov.todolist.presentation.description

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import ru.ayunusov.todolist.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ayunusov.todolist.data.repository.IDeleteInteractorTest
import ru.ayunusov.todolist.data.repository.ITaskInteractorTest
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.toLong
import java.time.LocalDateTime

@MediumTest
@RunWith(AndroidJUnit4::class)
class DescriptionDoFragmentTest {

    @RelaxedMockK
    lateinit var navController: NavController

    private val dateStart = LocalDateTime.of(2023, 2, 19, 20, 0)
    private val dateEnd = LocalDateTime.of(2023, 2, 19, 21, 0)
    private val task = Task(13, dateStart.toLong(), dateEnd.toLong(), "New Task", "Desc task")

    private val iDeleteTaskInteractor = IDeleteInteractorTest()
    private val iTasIDeleteInteractor = ITaskInteractorTest(task)

    var viewmodelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DescViewModel(iTasIDeleteInteractor, iDeleteTaskInteractor) as T
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val bundle = DescriptionDoFragmentArgs(task.id).toBundle()
        val scenario =
            launchFragmentInContainer<DescriptionDoFragment>(bundle, R.style.Theme_ToDoList) {
                DescriptionDoFragment().also { fragment ->
                    fragment.viewModelFactory = viewmodelFactory
                    fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                        if (viewLifecycleOwner != null) {
                            Navigation.setViewNavController(fragment.requireView(), navController)
                        }
                    }
                }
            }
    }

    @Test
    fun activeDesc_DisplayedInUI() {
        onView(withId(R.id.nameTaskTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.nameTaskTextView)).check(matches(withText(task.name)))

        onView(withId(R.id.dateTaskTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.dateTaskTextView)).check(matches(withText(task.getDate())))

        onView(withId(R.id.timeTaskTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.timeTaskTextView)).check(matches(withText("${task.getDateStartTime()} - ${task.getDateEndTime()}")))

        onView(withId(R.id.descTaskTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.descTaskTextView)).check(matches(withText(task.description)))

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.editTask)).check(matches(isDisplayed()))
        onView(withId(R.id.deleteTask)).check(matches(isDisplayed()))
    }

    @Test
    fun clickEditTask_navigateToAddTaskFragment() {
        onView(withId(R.id.editTask)).perform(click())
        verify {
            navController.navigate(
                DescriptionDoFragmentDirections.actionDescriptionDoFragmentToAddTaskFragment(
                    taskID = task.id
                )
            )
        }
    }

    @Test
    fun clickDelete_navigateToBack() {
        onView(withId(R.id.deleteTask)).perform(click())
        verify {
            navController.popBackStack()
        }
    }
}
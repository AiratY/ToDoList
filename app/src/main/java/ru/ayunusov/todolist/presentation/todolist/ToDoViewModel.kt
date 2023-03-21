package ru.ayunusov.todolist.presentation.todolist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ayunusov.todolist.domain.IGetListTasksInteractor
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.Event
import ru.ayunusov.todolist.other.toLong
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ToDoViewModel @Inject constructor(private val iGetListTasksInteractor: IGetListTasksInteractor) :
    ViewModel() {

    private val _isVisibleNoTask = MutableLiveData<Boolean>()
    val isVisibleNoTask: LiveData<Boolean> get() = _isVisibleNoTask

    private val _listTask = MutableLiveData<List<Task>>()
    val listTask: LiveData<List<Task>> get() = _listTask

    private val _isAddNewTask = MutableLiveData<Event<Long>>()
    val isAddNewTask: LiveData<Event<Long>> get() = _isAddNewTask

    private val _openTaskEvent = MutableLiveData<Event<Int>>()
    val openTaskEvent: LiveData<Event<Int>> = _openTaskEvent

    private var date: LocalDateTime = LocalDateTime.now()
    val calendarDate get() = date.toLong()

    init {
        updateList()
    }

    /**
     * Добавить новую задачу
     * */
    fun addNewTask(view: View) {
        _isAddNewTask.value = Event(date.toLong())
    }

    /**
     * Изменился день в календаре
     * */
    fun changeDay(view: View, year: Int, month: Int, day: Int) {
        date = LocalDateTime.of(LocalDate.of(year, month + 1, day), date.toLocalTime())
        updateList()
    }

    /**
     * Открывет экран с детальным описанием
     * */
    fun openTask(id: Int) {
        _openTaskEvent.value = Event(id)
    }

    /**
     * Обновляет список задач
     * */
    private fun updateList() {
        viewModelScope.launch {
            iGetListTasksInteractor.getTasks(date.toLocalDate()).collect { tasks ->
                _listTask.value = tasks
                _isVisibleNoTask.value = tasks.isEmpty()
            }
        }
    }
}
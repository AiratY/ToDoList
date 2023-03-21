package ru.ayunusov.todolist.presentation.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ayunusov.todolist.domain.IDeleteTaskInteractor
import ru.ayunusov.todolist.domain.ITaskInteractor
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.Event
import javax.inject.Inject

class DescViewModel @Inject constructor(
    private val iTaskInteractor: ITaskInteractor,
    private val iDeleteTaskInteractor: IDeleteTaskInteractor
) :
    ViewModel() {

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task> get() = _task

    private val _isNavigateUp = MutableLiveData<Event<Boolean>>()
    val isNavigateUp: LiveData<Event<Boolean>> get() = _isNavigateUp

    private val _isNavigateToEdit = MutableLiveData<Event<Int>>()
    val isNavigateToEdit: LiveData<Event<Int>> get() = _isNavigateToEdit

    /**
     * Задаёт ID задачи
     * @param taskId - ID задачи
     * */
    fun setID(taskId: Int) {
        viewModelScope.launch {
            iTaskInteractor.getTask(taskId).collect {
                _task.value = it
            }
        }
    }

    /**
     * Редактировать задачу
     * */
    fun editTask(): Boolean {
        task.value?.let { _isNavigateToEdit.value = Event(it.id) }
        return true
    }

    /**
     * Удалить задачу
     * */
    fun deleteTask(): Boolean {
        viewModelScope.launch {
            task.value?.let { iDeleteTaskInteractor.deleteTask(it) }
        }
        _isNavigateUp.value = Event(true)
        return true
    }
}
package ru.ayunusov.todolist.presentation.add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ayunusov.todolist.di.FragmentScope
import ru.ayunusov.todolist.domain.IAddTaskInteractor
import ru.ayunusov.todolist.domain.ITaskInteractor
import ru.ayunusov.todolist.domain.model.Task
import ru.ayunusov.todolist.other.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@FragmentScope
class AddTaskViewModel @Inject constructor(
    private val iAddTaskInteractor: IAddTaskInteractor,
    private val iTaskInteractor: ITaskInteractor
) :
    ViewModel() {

    private val _isShowError = MutableLiveData<Event<Boolean>>()
    val isShowError: LiveData<Event<Boolean>> get() = _isShowError

    private val _isNavigate = MutableLiveData<Event<NAVIGATE>>()
    val isNavigate get() = _isNavigate
    private var currentNavigate: NAVIGATE = NAVIGATE.NAVIGATE_BACK

    private val _isVisibilityDatePicker = mutableStateOf(false)
    val isVisibilityDatePicker get() = _isVisibilityDatePicker

    private val _isVisibilityTimePicker = mutableStateOf(false)
    val isVisibilityTimePicker get() = _isVisibilityTimePicker

    private var id = 0
    val nameTask = MutableLiveData<String>()
    val descTask = MutableLiveData<String>()
    val hour = MutableLiveData<Int>()
    val minute = MutableLiveData<Int>()
    val year = MutableLiveData<Int>()
    val month = MutableLiveData<Int>()
    val day = MutableLiveData<Int>(1)

    private var currentStartDateTime: LocalDateTime? = null
    private var currentEndDateTime: LocalDateTime? = null


    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate
    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> get() = _endDate
    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String> get() = _startTime
    private val _endTime = MutableLiveData<String>()
    val endTime: LiveData<String> get() = _endTime

    /**
     * Устанавливаем аргументы фрагмента
     * @param date - дата
     * @param taskID - ID задачи
     * */
    fun setArgs(date: Long, taskID: Int) {
        if (date != DEFAULT_DATE) {
            setupDate(date)
            currentNavigate = NAVIGATE.NAVIGATE_BACK
        } else if (taskID != DEFAULT_ID) {
            setupTask(taskID)
            currentNavigate = NAVIGATE.NAVIGATE_TO_LIST
        }
    }

    /**
     * Сохранение новой задачи
     * */
    fun saveTask(): Boolean {
        val name = nameTask.value
        if (name == null || name == "") {
            _isShowError.value = Event(true)
        } else {
            viewModelScope.launch {
                iAddTaskInteractor.addNewTask(getTask())
            }
            navigate()
        }
        return true
    }

    /**
     * OnDismiss DatePickerDialog
     * */
    fun onDatePickerDismiss() {
        _isVisibilityDatePicker.value = false
    }

    /**
     * onClick на выбор новой даты
     * */
    fun onSelectNewDate() {
        _isVisibilityDatePicker.value = true
    }

    /**
     * onClick на выбор нового времени
     * */
    fun onSelectNewTime() {
        _isVisibilityTimePicker.value = true
    }

    /**
     * Listener изменения даты в DatePickerDialog
     * @param mYear - год
     * @param mMonth - месяц
     * @param mDay - день
     * */
    fun onDateChange(mYear: Int, mMonth: Int, mDay: Int) {
        year.value = mYear
        month.value = mMonth
        day.value = mDay

        val selectedDate = LocalDate.of(mYear, mMonth + 1, mDay)

        currentStartDateTime = LocalDateTime.of(selectedDate, currentStartDateTime?.toLocalTime())
        currentEndDateTime = currentStartDateTime?.plusHours(DIFF_TASK)

        updateDateTime()
    }

    /**
     * Listener изменения времени в onTimeChange
     * @param mHour - часы
     * @param mMinute - минуты
     * */
    fun onTimeChange(mHour: Int, mMinute: Int) {
        hour.value = mHour
        minute.value = mMinute

        val selectedTime = LocalTime.of(mHour, mMinute)

        currentStartDateTime = LocalDateTime.of(currentStartDateTime?.toLocalDate(), selectedTime)
        currentEndDateTime = currentStartDateTime?.plusHours(DIFF_TASK)

        updateDateTime()
    }

    /**
     * Обработчик скрытия TimePicker
     * обновляет флаг на показ TimePiker
     * */
    fun onTimePickerDismiss() {
        _isVisibilityTimePicker.value = false
    }

    /**
     * Обновляет текст даты и времени
     * */
    private fun updateDateTime() {
        _startDate.value = currentStartDateTime?.toDateString()
        _startTime.value = currentStartDateTime?.toTimeString()
        _endDate.value = currentEndDateTime?.toDateString()
        _endTime.value = currentEndDateTime?.toTimeString()
    }

    /**
     * Создает новую задачу
     * */
    private fun getTask(): Task {
        val nameTask = nameTask.value ?: ""
        val descTask = descTask.value ?: ""

        return Task(
            id,
            currentStartDateTime?.toLong() ?: 0L,
            currentEndDateTime?.toLong() ?: 0L,
            nameTask,
            descTask
        )
    }

    /**
     * Задаёт параметры для отображения даты и времени
     * @param date - дата и время в формате Long
     * */
    private fun setupDate(date: Long) {
        val startDate = date.toLocalDateTime()
        val endDate = startDate.plusHours(DIFF_TASK)

        day.value = startDate.dayOfMonth
        month.value = startDate.monthValue - 1
        year.value = startDate.year
        hour.value = startDate.hour
        minute.value = startDate.minute

        currentStartDateTime = startDate
        currentEndDateTime = endDate

        updateDateTime()
    }

    /**
     * Осуществляет переход на экран со списком задач
     * */
    private fun navigate() {
        _isNavigate.value = Event(currentNavigate)
    }

    /**
     * Загружает данные об редактируемой задачи,
     * инициализирует поля данными задачи
     * */
    private fun setupTask(taskID: Int) {
        viewModelScope.launch {
            iTaskInteractor.getTask(taskID).collect { task ->
                id = task.id
                nameTask.value = task.name
                descTask.value = task.description
                setupDate(task.dateStart)
            }
        }
    }

    companion object {
        private const val DEFAULT_DATE = -1L
        private const val DEFAULT_ID = -1
        private const val DIFF_TASK = 1L

        enum class NAVIGATE {
            NAVIGATE_BACK, NAVIGATE_TO_LIST
        }
    }
}
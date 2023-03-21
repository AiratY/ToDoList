package ru.ayunusov.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.ayunusov.todolist.other.toDateString
import ru.ayunusov.todolist.other.toLocalDateTime
import ru.ayunusov.todolist.other.toTimeString

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val dateStart: Long,
    val dateEnd: Long,
    val name: String,
    val description: String
) {
    fun getDateStartTime() = dateStart.toLocalDateTime().toTimeString()
    fun getDateEndTime() = dateEnd.toLocalDateTime().toTimeString()

    /**
     * Возвращает в виде строки дату или даты если различны
     * */
    fun getDate(): String {
        val dateStart = dateStart.toLocalDateTime()
        val dateEnd = dateEnd.toLocalDateTime()
        return if (dateStart.toLocalDate() != dateEnd.toLocalDate()) {
            "${dateStart.toDateString()} - ${dateEnd.toDateString()}"
        } else {
            dateStart.toDateString()
        }
    }
}
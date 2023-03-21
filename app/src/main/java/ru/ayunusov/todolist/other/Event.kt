package ru.ayunusov.todolist.other

/**
 * Класс обёртка для выполнения события один раз
 * */
class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandle(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
package ru.ayunusov.todolist.other

import java.time.*
import java.time.format.DateTimeFormatter

fun LocalDateTime.toLong(): Long {
    return ZonedDateTime.of(this, ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
}

/**
 * Преобразует LocalDateTime в строку времени
 * */
fun LocalDateTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Преобразует LocalTime в строку времени
 * */
fun LocalTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

/**
 * Преобразует LocalDateTime в строку даты
 * */
fun LocalDateTime.toDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

/**
 * Преобразует LocalDate в строку даты
 * */
fun LocalDate.toDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}


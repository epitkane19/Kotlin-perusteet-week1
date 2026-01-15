package com.epitkane19.week1.domain

fun addTask(list: List<Task>, task: Task): List<Task> =
    list + task

fun removeTask(list: List<Task>, id: Int): List<Task> =
    list.filter { it.id != id }

fun toggleDone(list: List<Task>, id: Int): List<Task> =
    list.map { task ->
        if (task.id == id) task.copy(done = !task.done) else task
    }

fun filterByDone(list: List<Task>, done: Boolean): List<Task> =
    list.filter { it.done == done }

fun sortByDueDate(list: List<Task>): List<Task> =
    list.sortedBy { it.dueDate }



package com.epitkane19.week1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.epitkane19.week1.model.Task
import com.epitkane19.week1.model.mockTasks

class TaskViewModel : ViewModel() {

    private var allTasks = mutableStateListOf<Task>()
    var tasks by mutableStateOf(listOf<Task>())
        private set

    init {
        allTasks.addAll(mockTasks)
        tasks = allTasks.toList()
    }

    fun addTask(task: Task) {
        allTasks.add(task)
        tasks = allTasks.toList()
    }

    fun removeTask(id: Int) {
        allTasks.removeAll { it.id == id }
        tasks = allTasks.toList()
    }

    fun toggleDone(id: Int) {
        allTasks = allTasks.map { t ->
            if (t.id == id) t.copy(done = !t.done) else t }.toMutableStateList()
        tasks = allTasks.toList()
    }

    fun filterByDone(done: Boolean) {
        tasks = allTasks.filter { it.done == done }
    }

    fun sortByDueDate() {
        tasks = allTasks.sortedBy { it.dueDate }
    }

    fun showAll() {
        tasks = allTasks.toList()
    }

    fun updateTask(updatedTask: Task) {
        allTasks = allTasks.map { t ->
            if (t.id == updatedTask.id) updatedTask else t }.toMutableStateList()
        tasks = allTasks.toList()
    }
}

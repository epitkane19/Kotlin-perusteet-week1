package com.epitkane19.week1.domain

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.width


@Composable
fun HomeScreen(tasks: List<Task>, modifier: Modifier = Modifier) {

    var tasks by remember { mutableStateOf<List<Task>>(tasks) }
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var filterDone by remember { mutableStateOf<Boolean?>(null) }

    // Suodatettu lista
    val visibleTasks = when (filterDone) {
        true -> tasks.filter { it.done }
        false -> tasks.filter { !it.done }
        else -> tasks
    }

    Column(modifier = modifier.padding(16.dp)) {

        Text("Task List", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Uuden tehtävän lisäys
        OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = newDescription,
            onValueChange = { newDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTitle.isNotBlank()) {
                    val newTask = Task(
                        id = tasks.size + 1,
                        title = newTitle,
                        description = newDescription,
                        priority = 1,
                        dueDate = "2026-01-20",
                        done = false
                    )
                    tasks = addTask(tasks, newTask)
                    newTitle = ""
                    newDescription = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(Modifier.height(16.dp))

        // Suodatusnapit
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { filterDone = null }) { Text("All") }
            Button(onClick = { filterDone = false }) { Text("Undone") }
            Button(onClick = { filterDone = true }) { Text("Done") }
        }

        Spacer(Modifier.height(16.dp))

        // Sort by date -toiminto
        Button(
            onClick = { tasks = sortByDueDate(tasks) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sort by date")
        }

        Spacer(Modifier.height(16.dp))

        // Tehtävälista
        visibleTasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = {
                            tasks = toggleDone(tasks, task.id)
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(task.title)
                }


                Row {
                    Button(onClick = {
                        tasks = removeTask(tasks, task.id)
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}




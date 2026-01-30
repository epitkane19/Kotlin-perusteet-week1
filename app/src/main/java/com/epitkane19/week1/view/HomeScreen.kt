package com.epitkane19.week1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.width
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.epitkane19.week1.model.Task
import com.epitkane19.week1.viewmodel.TaskViewModel
import androidx.compose.foundation.clickable


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel()
) {
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }
    var newDueDate by remember { mutableStateOf("") }
    var editTask by remember { mutableStateOf<Task?>(null) }

    Column(modifier = modifier
        .systemBarsPadding()
        .padding(16.dp)
    ) {

        Text("Task List", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        // Uuden tehtävän lisäys
        TextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        TextField(
            value = newDescription,
            onValueChange = { newDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        TextField(
            value = newDueDate,
            onValueChange = { newDueDate = it },
            label = { Text("Due date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTitle.isNotBlank()) {
                    taskViewModel.addTask(
                        Task(
                            id = taskViewModel.tasks.size + 1,
                            title = newTitle,
                            description = newDescription,
                            priority = 1,
                            dueDate = newDueDate,
                            done = false
                        )
                    )
                    newTitle = ""
                    newDescription = ""
                    newDueDate = ""
                }
            }
        ) {
            Text("Add Task")
        }


        Spacer(Modifier.height(16.dp))

        // Suodatusnapit
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { taskViewModel.showAll() }) { Text("All") }
            Button(onClick = { taskViewModel.filterByDone(false) }) { Text("Undone") }
            Button(onClick = { taskViewModel.filterByDone(true) }) { Text("Done") }
        }

        Spacer(Modifier.height(16.dp))

        // Sort by date -toiminto
        Button(
            onClick = { taskViewModel.sortByDueDate() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sort by date")
        }

        Spacer(Modifier.height(16.dp))

        // Tehtävälista
        LazyColumn {
            items(taskViewModel.tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { taskViewModel.toggleDone(task.id) }
                        )
                        Spacer(Modifier.width(8.dp))

                        Column(
                            modifier = Modifier.clickable { editTask = task }
                        ) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Text(
                                text = task.description,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            task.dueDate?.let {
                                Text(
                                    text = "Due: $it",
                                    fontSize = 12.sp,
                                    //color = Color.Gray,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    editTask?.let { task ->
        DetailScreen(
            task = task,
            onDismiss = { editTask = null },
            taskViewModel = taskViewModel
        )
    }

}

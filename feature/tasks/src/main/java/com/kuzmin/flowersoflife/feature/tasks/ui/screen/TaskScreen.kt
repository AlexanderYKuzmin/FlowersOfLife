package com.kuzmin.flowersoflife.feature.tasks.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.feature.tasks.ui.viewmodels.TasksViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
    //onTaskClick: (taskId: String) -> Unit
) {
    //val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Задачи") },
                navigationIcon = { /* Add navigation icon if needed */ }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            /*items(uiState.tasks) { task ->
                TaskItem(
                    task = task,
                    onClick = { onTaskClick(task.id) }
                )
            }*/
        }
    }
}

/*
@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.h6)
            Text(text = task.description, style = MaterialTheme.typography.body2)
        }
    }
}*/

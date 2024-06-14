package com.example.todoapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.presentation.component.EditDialog
import com.example.todoapp.presentation.component.TaskListItems
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Surface(modifier = Modifier.fillMaxWidth()) {

                    MainContents()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContents(viewModel: MainViewModel = hiltViewModel()) {
    val isShowDialog = remember {
        mutableStateOf(false)
    }

    if (isShowDialog.value) {
        EditDialog(isShowDialog = isShowDialog)
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { isShowDialog.value = true }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "新規タスク作成ボタン")
        }
    }) {
        Spacer(modifier = Modifier.height(12.dp))
        val taskList by viewModel.tasks.collectAsState(initial = emptyList())
        TaskListItems(
            tasks = taskList,
            onClickCard = {
                viewModel.onClickCard(it)
                isShowDialog.value = true
            },
            onClickDelete = {
                viewModel.deleteTask(it)
            },
        )


    }
}
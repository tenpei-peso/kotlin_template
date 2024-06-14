package com.example.todoapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.entity.Task
import com.example.todoapp.repository.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val taskDao: TaskDao) : ViewModel() {
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    val tasks = taskDao.getAllTasks().distinctUntilChanged()
    var isEditing = false


    fun upsertTask() {
        //upsertしても新規で作成されてしまう。idも新しくなっている
        //おそらく、Taskをここでinstance化しているため新しいTaskが作成されている
        viewModelScope.launch {
            val task = Task(
                title = title,
                description = description
            )

            taskDao.upsertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun onClickCard(task: Task) {
        Log.d(MainViewModel::class.simpleName, "onClickCard: $task")
        isEditing = true
        title = task.title
        description = task.description
    }
}
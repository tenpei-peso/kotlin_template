package com.example.todoapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.viewmodel.MainViewModel

@Composable
fun EditDialog(
    isShowDialog: MutableState<Boolean>,
    viewModel: MainViewModel = hiltViewModel(),
) {
    // ダイアログが破棄された時
    DisposableEffect(Unit) {
        onDispose {
            viewModel.title = ""
            viewModel.description = ""
            viewModel.isEditing = false
        }
    }

    AlertDialog(
        onDismissRequest = { isShowDialog.value = false },
        confirmButton = {},
        title = {
            Text(
                text =
                if (viewModel.isEditing) "タスクを編集中"
                else "タスクを新規作成"
            )
        },
        text = {
            DialogTextContents()
        },
        dismissButton = { DialogButtonContents(isShowDialog = isShowDialog) }
    )
}

@Composable
fun DialogTextContents(viewModel: MainViewModel = hiltViewModel()) {
    Column {
        Text(text = "タイトル")
        TextField(
            value = viewModel.title,
            onValueChange = { viewModel.title = it },
        )
        Text(text = "詳細")
        TextField(
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
        )
    }
}

@Composable
fun DialogButtonContents(
    isShowDialog: MutableState<Boolean>,
    viewModel: MainViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.End
    )
    {
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { isShowDialog.value = false }, modifier = Modifier.width(120.dp)) {
            Text(text = "キャンセル")

        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                viewModel.upsertTask()
                isShowDialog.value = false
            },
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = "作成")
        }
    }
}
package com.example.todoapp.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.entity.Task
import com.example.todoapp.repository.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
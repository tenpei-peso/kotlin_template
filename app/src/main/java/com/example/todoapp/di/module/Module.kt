package com.example.todoapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.todoapp.di.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_database"
        ).build()

    }

    @Provides
    fun provideTaskDao(database: AppDatabase) = database.taskDao()
}
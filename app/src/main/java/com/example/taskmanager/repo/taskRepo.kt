package com.example.taskmanager.repo

import androidx.room.Query
import com.example.taskmanager.database.TaskDatabase
import com.example.taskmanager.model.Task

class taskRepo (private val db: TaskDatabase){

    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)
    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)
    suspend fun  updateTask(task: Task) = db.getTaskDao().updateTask(task)

    fun getAllTasks()= db.getTaskDao().getAllTasks()
    fun searchTask(query: String?) = db.getTaskDao().searchTask(query)

}
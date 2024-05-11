package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.taskmanager.model.Task
import com.example.taskmanager.repo.taskRepo
import kotlinx.coroutines.launch

class TaskViewModel(app:Application,private val taskRepo: taskRepo): AndroidViewModel(app) {


    fun addTask(task: Task)=
        viewModelScope.launch {
            taskRepo.insertTask(task)
         }

    fun deleteTask(task: Task)=
        viewModelScope.launch {
            taskRepo.deleteTask(task)
        }

    fun updateTask(task: Task)=
        viewModelScope.launch {
            taskRepo.updateTask(task)
        }

    fun getAllTasks() = taskRepo.getAllTasks()

    fun searchTask(query: String?) =
        taskRepo.searchTask(query)

}


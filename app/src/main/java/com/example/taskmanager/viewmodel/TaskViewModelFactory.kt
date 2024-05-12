package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.repo.taskRepo

class TaskViewModelFactory (val app:Application,private val taskRepo: taskRepo): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(app,taskRepo) as T
    }

}
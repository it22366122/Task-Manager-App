package com.example.taskmanager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.database.TaskDatabase
import com.example.taskmanager.repo.taskRepo
import com.example.taskmanager.viewmodel.TaskViewModel
import com.example.taskmanager.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupViewModel()

    }
     private fun  setupViewModel(){
         val taskRepo = taskRepo(TaskDatabase(this))
         val viewModelProviderFactory = TaskViewModelFactory(application,taskRepo)
         taskViewModel = ViewModelProvider(this,viewModelProviderFactory)[TaskViewModel::class.java]

     }

}
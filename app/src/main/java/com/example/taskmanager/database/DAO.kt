package com.example.taskmanager.database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.model.Task


@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //if same primary key is existing replace new data with old ones
    suspend fun  insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id DESC  ")
    fun getAllTasks() : LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE title LIKE :query ")
    fun searchTask(query: String?) : LiveData<List<Task>>
}
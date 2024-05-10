package com.example.taskmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.model.Task

@Database(entities = [Task::class] , version = 1)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun getTaskDao(): DAO

    companion object{
        @Volatile
        private  var instance :TaskDatabase ?= null
        private  val LOCK = Any()   // only one thread can execute at once


        operator  fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }


        private  fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
               TaskDatabase::class.java,
                "task_db"
            ).build()


    }



}

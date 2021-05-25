package com.example.taskdl.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.task.room.dao.EmpResponseDao
import com.example.taskdl.model.MatchesResponseModel

@Database(entities = [MatchesResponseModel::class],version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun empResponseDao(): EmpResponseDao
    companion object {

        /*
        * Instantiate Local Database for Application
        * */
        private var applicationDatabase: AppDb? = null


        fun getInstance(context: Context): AppDb {
            synchronized(this) {
                var instance =
                    applicationDatabase

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDb::class.java,
                        "UserDB.db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    applicationDatabase = instance
                }
                return instance
            }
        }

    }
}
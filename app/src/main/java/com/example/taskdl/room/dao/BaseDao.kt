package com.example.task.room.dao

import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(obj: T): Long

    @Insert
    fun insert(obj: List<T>)

    @Update
    fun update(obj: T)

    @Update
    fun update(obj: List<T>)
}
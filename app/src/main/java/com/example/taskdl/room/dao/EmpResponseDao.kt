package com.example.task.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.taskdl.model.MatchesResponseModel

@Dao
abstract class EmpResponseDao : BaseDao<MatchesResponseModel>{

    @Query("SELECT * FROM MatchesResponseModel")
    abstract fun getAllEmp(): List<MatchesResponseModel>

    @Query("SELECT * FROM MatchesResponseModel WHERE status = :status")
    abstract fun getActiveEmp(status: Boolean): List<MatchesResponseModel>

    @Query("DELETE FROM MatchesResponseModel")
    abstract fun deleteAllEmp()

    @Query("UPDATE MatchesResponseModel SET status = :status WHERE id = :id")
    abstract fun updateEmpById(
        status: Boolean,
        id: Int
    )

}
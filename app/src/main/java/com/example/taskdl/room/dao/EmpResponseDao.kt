package com.example.task.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.taskdl.model.EmployeesResponseModel

@Dao
abstract class EmpResponseDao : BaseDao<EmployeesResponseModel>{

    @Query("SELECT * FROM EmployeesResponseModel")
    abstract fun getAllEmp(): List<EmployeesResponseModel>

    @Query("SELECT * FROM EmployeesResponseModel WHERE status = :status")
    abstract fun getActiveEmp(status: Boolean): List<EmployeesResponseModel>

    @Query("DELETE FROM EmployeesResponseModel")
    abstract fun deleteAllEmp()

    @Query("UPDATE EmployeesResponseModel SET status = :status WHERE id = :id")
    abstract fun updateEmpById(
        status: Boolean,
        id: Int
    )

}
package com.example.taskdl.api

import com.example.taskdl.model.EmployeesResponseModel


interface ApiHelper {
    suspend fun getMatches(): ArrayList<EmployeesResponseModel>
}
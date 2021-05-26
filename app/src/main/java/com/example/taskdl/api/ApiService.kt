package com.example.taskdl.api

import com.example.taskdl.model.EmployeesResponseModel
import com.example.taskdl.model.ResponseModel
import retrofit2.http.GET

interface ApiService {

    @GET("v1/employees")
    suspend fun getMatches(): ResponseModel<ArrayList<EmployeesResponseModel>>
}
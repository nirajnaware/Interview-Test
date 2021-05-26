package com.example.taskdl.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskdl.api.ApiService

class ViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EmployeesViewModel::class.java)) {
            return EmployeesViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
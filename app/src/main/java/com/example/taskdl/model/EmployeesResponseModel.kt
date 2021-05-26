package com.example.taskdl.model

import android.graphics.drawable.Drawable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskdl.BR
import com.example.taskdl.R

@Entity
class EmployeesResponseModel : BaseObservable(){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var employee_name: String = ""
    var employee_salary: Int = 0

    var employeeSalary = ""
        get() {
            return employee_salary.toString()
        }

    var employee_age: Int = 0

    var employeeAge = ""
        get() {
            return employee_age.toString()
        }

    var profile_image: String = ""
    var status: Boolean = false

    @Bindable
    var statusDrawable = R.drawable.ic_inactive
        set(value) {
            field = value
            notifyPropertyChanged(BR.statusDrawable)
        }
}
package com.example.taskdl.view_model

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskdl.R
import com.example.taskdl.api.ApiService
import com.example.taskdl.model.EmployeesResponseModel
import com.example.taskdl.room.db.AppDb
import com.example.taskdl.utils.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.URL

class EmployeesViewModel(private val apiService: ApiService) : ViewModel() {


    var matchesResponseModelListLiveData =
        MutableLiveData<Resource<List<EmployeesResponseModel>>>()
    var onClickStarLiveData = MutableLiveData<EmployeesResponseModel>().apply { value = null }
    var cardPosition: Int = -1

    fun getEmp(mContext: Context) {

        AppDb.getInstance(mContext).empResponseDao().apply {
            if (this.getAllEmp().isEmpty()) {
                // delete the data from local db
                deleteAllEmp()

                viewModelScope.launch {
                    try {
                        val res = apiService.getMatches().data
                        insert(res)
                        matchesResponseModelListLiveData.postValue(Resource.success(res))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        matchesResponseModelListLiveData.postValue(
                            Resource.error(
                                keyNoInternetException,
                                null
                            )
                        )
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        matchesResponseModelListLiveData.postValue(
                            Resource.error(
                                keyAPIException,
                                null
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        matchesResponseModelListLiveData.postValue(
                            Resource.error(
                                e.message?.toString() ?: "", null
                            )
                        )
                    }
                }
            } else {
                matchesResponseModelListLiveData.postValue(Resource.success(getAllEmp()))
            }
        }

    }

    fun getSavedEmp(mContext: Context) {
        AppDb.getInstance(mContext).empResponseDao().apply {
            matchesResponseModelListLiveData.postValue(Resource.success(getActiveEmp(true)))
        }
    }

    /*
   * Set click listner on star
   * */
    fun onClickStar(
        view: View,
        position: Int,
        pageType: String,
        masterModel: EmployeesResponseModel
    ) {
        try {
            cardPosition = position
            if (pageType == keyAllEmp) {
                if (!masterModel.status) {
                    view.setBackgroundResource(R.drawable.ic_active)
                    onClickStarLiveData.value = masterModel
                }
            } else {
                if (masterModel.status) {
                    view.setBackgroundResource(R.drawable.ic_inactive)
                    onClickStarLiveData.value = masterModel
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
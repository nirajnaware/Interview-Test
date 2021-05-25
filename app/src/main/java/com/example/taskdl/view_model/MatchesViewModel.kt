package com.example.taskdl.view_model

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskdl.R
import com.example.taskdl.api.ApiService
import com.example.taskdl.model.MatchesResponseModel
import com.example.taskdl.room.db.AppDb
import com.example.taskdl.utils.keyAllEmp
import kotlinx.coroutines.launch
import java.net.URL

class MatchesViewModel(private val apiService: ApiService) : ViewModel() {

    var getUrl: URL? = null
    var matchesResponseModelList = ArrayList<MatchesResponseModel>()
    var apiResponse = MutableLiveData<Boolean>().apply { value = null }
    var matchesResponseModelListLiveData =
        MutableLiveData<List<MatchesResponseModel>>().apply { value = arrayListOf() }
    var onClickStarLiveData = MutableLiveData<MatchesResponseModel>().apply { value = null }
    var cardPosition: Int = -1

    fun getEmp(mContext: Context) {
        try {
            AppDb.getInstance(mContext).empResponseDao().apply {
                if (this.getAllEmp().isEmpty()) {
                    // delete the data from local db
                    deleteAllEmp()
                    // add the movie into local db
                    viewModelScope.launch {
                        val res = apiService.getMatches().data
                        insert(res)
                        matchesResponseModelListLiveData.postValue(res)
                    }
                } else {
                    matchesResponseModelListLiveData.postValue(getAllEmp())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
   * Set click listner on star
   * */
    fun onClickStar(view: View, position: Int,pageType: String, masterModel: MatchesResponseModel) {
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
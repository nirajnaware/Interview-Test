package com.example.taskdl.api

import com.example.taskdl.model.MatchesResponseModel
import com.example.taskdl.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    //@GET("v2/venues/search?ll=40.7484,-73.9857&oauth_token=NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ&v=20180616")
    //@GET("activities/types")
    @GET("v1/employees")
    suspend fun getMatches(): ResponseModel<ArrayList<MatchesResponseModel>>
}
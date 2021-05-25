package com.example.taskdl

import com.example.taskdl.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Retrofit is a REST Client for Java and Android.
* It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice.
* In Retrofit you configure which converter is used for the data serialization.
* */
class RetrofitInitializer {

    //val BASE_URL ="https://api.foursquare.com/"
    //val BASE_URL ="http://webapptest.classroomplus.in/SalesTracker/test/api/"
    val BASE_URL ="http://dummy.restapiexample.com/api/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}
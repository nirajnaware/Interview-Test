package com.example.taskdl.api

import com.example.taskdl.model.MatchesResponseModel


interface ApiHelper {
    suspend fun getMatches(): ArrayList<MatchesResponseModel>
}
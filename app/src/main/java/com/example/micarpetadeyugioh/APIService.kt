package com.example.micarpetadeyugioh

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun geCardInformation(@Url url:String): Response<CardResponse>

    // @GET
    // fun getCardImage(@Url url: String): Response

}
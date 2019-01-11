package com.radlab.testfixer.network

import com.radlab.testfixer.data.FixerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @get:GET("latest?access_key=591bba45856ba1a8e06da422940ea11e")
    val getResponse: Call<FixerResponse>

    @GET( "{date}?access_key=591bba45856ba1a8e06da422940ea11e")
    fun getMoreResponse(@Path("date") date: String): Call<FixerResponse>

    companion object {

        val BASE_URL = "http://data.fixer.io/api/"
    }
}

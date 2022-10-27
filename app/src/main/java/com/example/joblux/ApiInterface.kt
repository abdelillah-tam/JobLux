package com.example.joblux

import com.example.joblux.domain.models.JobSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("jobs/{country}/search/1")
    fun getJobSearch(
        @Path("country") country: String,
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("results_per_page") results: Int,
        @Query("what") what: String
    ): Call<JobSearch>

}
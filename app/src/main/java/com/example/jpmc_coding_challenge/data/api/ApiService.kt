package com.example.jpmc_coding_challenge.data.api

import com.example.jpmc_coding_challenge.data.model.NYCSchool
import com.example.jpmc_coding_challenge.data.model.SatScore
import com.example.jpmc_coding_challenge.util.RESOURCE
import com.example.jpmc_coding_challenge.util.SAT_SCORES
import com.example.jpmc_coding_challenge.util.SCHOOLS
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("$RESOURCE$SCHOOLS")
    suspend fun fetchSchools(): Response<List<NYCSchool>>

    @GET("$RESOURCE$SAT_SCORES")
    suspend fun fetchSatScores(): Response<List<SatScore>>
}
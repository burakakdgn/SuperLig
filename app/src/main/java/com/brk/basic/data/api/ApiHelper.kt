package com.brk.basic.data.api

import retrofit2.http.Query

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun getFixtures() = apiInterface.getFixtures()


    suspend fun getFixturesDate(league: Int, season: Int,from: String, to: String) = apiInterface.getFixturesDate(league,season,from,to)
}
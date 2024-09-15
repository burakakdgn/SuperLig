package com.brk.basic.data.api


import com.brk.basic.model.responses.FixtureResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ApiInterface {
    // Örnek olarak, belirli bir ligden maç sonuçlarını almak için bir endpoint
    @GET("v3/fixtures?league=203&season=2023&from=2023-11-09&to=2023-11-10")
    suspend fun getFixtures(): Response<FixtureResponse>

    @GET("v3/fixtures")
    suspend fun getFixturesDate(
        @Query("league") league: Int,
        @Query("season") season: Int,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<FixtureResponse>
}
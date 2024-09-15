package com.brk.basic.data.repositories

import com.brk.basic.data.api.ApiHelper

class GenelRepository(private val apiHelper: ApiHelper) {
    suspend fun getFixtures() = apiHelper.getFixtures()

    suspend fun getFixturesDate(league: Int, season: Int,from: String, to: String) = apiHelper.getFixturesDate(league,season,from,to)

}
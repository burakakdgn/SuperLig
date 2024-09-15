package com.brk.basic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brk.basic.data.repositories.GenelRepository

import androidx.lifecycle.liveData
import com.brk.basic.model.responses.FixtureResponse
import com.brk.basic.utils.Resource
import kotlinx.coroutines.Dispatchers

class GenelViewModel(private val genelRepository: GenelRepository): ViewModel() {


    fun getFixtures() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = genelRepository.getFixtures()
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body())) // body() ile FixtureResponse nesnesine erişim
            } else {
                emit(Resource.error(data = null, message = response.message()))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getFixturesDate(league: Int, season: Int, fromDate: String, toDate: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val response = genelRepository.getFixturesDate(league, season, fromDate, toDate)
            if (response.isSuccessful) {
                emit(Resource.success(data = response.body())) // body() ile FixtureResponse nesnesine erişim
            } else {
                emit(Resource.error(data = null, message = response.message()))
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}
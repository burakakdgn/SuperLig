package com.brk.basic.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brk.basic.data.api.ApiHelper
import com.brk.basic.data.repositories.GenelRepository
import com.brk.basic.viewmodel.GenelViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GenelViewModel::class.java)) {
            return GenelViewModel(GenelRepository(apiHelper)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
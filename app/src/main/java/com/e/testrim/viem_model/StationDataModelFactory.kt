package com.rimtest.viem_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rimtest.repositories.StationDataRepository

class StationDataModelFactory(
    private val stationDataRepository: StationDataRepository,
    private val stationName: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StationDataViewModel(stationDataRepository, stationName) as T
    }
}
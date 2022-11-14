package com.rimtest.viem_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.testrim.models.StationDataModel
import com.rimtest.repositories.StationDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationDataViewModel(private val stationDataRepository: StationDataRepository, private val stationName: String): ViewModel() {
    init {
        viewModelScope.launch (Dispatchers.IO) {

            stationDataRepository.getStationData(stationName)
        }
    }
    val getStationData: LiveData<StationDataModel>
    get() = stationDataRepository.stationData
}
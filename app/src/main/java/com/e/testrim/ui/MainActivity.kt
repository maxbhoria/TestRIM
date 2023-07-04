package com.e.testrim.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.testrim.R
import com.e.testrim.adapter.StationAdapter
import com.e.testrim.models.ArrayOfObjStationData
import com.rimtest.repositories.StationDataRepository
import com.rimtest.viem_model.StationDataModelFactory
import com.rimtest.viem_model.StationDataViewModel
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var stationDataViewModel: StationDataViewModel
    private lateinit var stationDataRepository: StationDataRepository
    private lateinit var customAdapter: StationAdapter
    private lateinit var buttonRefresh: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var arrayOfObjStationData: ArrayOfObjStationData
    private lateinit var stationRecyclerView: RecyclerView
    private lateinit var stationName: String

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        buttonRefresh = this.findViewById(R.id.buttonRefresh)
        progressBar = this.findViewById(R.id.progress_bar)
        stationRecyclerView = this.findViewById(R.id.stationRecyclerView)
        stationDataRepository = StationDataRepository(this)
        getStationData()
        buttonRefresh.setOnClickListener {
            stationDataRepository.getStationData(stationName)
            getStationData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun getStationData() {
        progressBar.visibility = View.VISIBLE
        stationRecyclerView.visibility = View.GONE
        //getting current device time
        val dateFormat = SimpleDateFormat("HH:mm")
        val millisInString: String = dateFormat.format(Date())
        val currentTime: LocalTime = LocalTime.parse(millisInString)
        //parsing northbound time
        val northBoundStartTime: LocalTime = LocalTime.parse("00:00")
        val northBoundEndTime: LocalTime = LocalTime.parse("12:00")
        //parsing southbound time
        val southBoundStartTime: LocalTime = LocalTime.parse("12:01")
        val southBoundEndTime: LocalTime = LocalTime.parse("23:59")
        if (currentTime.isAfter(northBoundStartTime) && currentTime.isBefore(northBoundEndTime)) {
            stationName = "perse"
            getData(stationName)
        } else if (currentTime.isAfter(southBoundStartTime) && currentTime.isBefore(
                southBoundEndTime
            )
        ) {
            stationName = "howth"
            getData(stationName)
        } else {
            Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_LONG).show()
            progressBar.visibility = View.GONE
        }
    }

    private fun getData(station: String) {

        stationDataViewModel =
            ViewModelProvider(
                this,
                StationDataModelFactory(stationDataRepository, station)
            )[StationDataViewModel::class.java]

        stationDataViewModel.getStationData.observe(this) {
            Log.d("Mohit", "onCreate ${it.ArrayOfObjStationData}")
            arrayOfObjStationData = it.ArrayOfObjStationData
            progressBar.visibility = View.GONE
            stationRecyclerView.visibility = View.VISIBLE
            if (arrayOfObjStationData.objStationData.isNotEmpty()) {
                setUpUi()
            } else {
                Toast.makeText(this, R.string.no_data_available, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setUpUi() {
        customAdapter = StationAdapter(arrayOfObjStationData.objStationData)
        val layoutManager = LinearLayoutManager(applicationContext)
        stationRecyclerView.layoutManager = layoutManager
        stationRecyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }
}

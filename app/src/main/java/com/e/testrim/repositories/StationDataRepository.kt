package com.rimtest.repositories


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.testrim.models.StationDataModel
import com.google.gson.Gson
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import org.json.JSONException


class StationDataRepository(private val context: Context) {
    private val stationMutableLiveData = MutableLiveData<StationDataModel>()

    val stationData: LiveData<StationDataModel>
        get() = stationMutableLiveData

    fun getStationData(stationName: String) {
        val apiUrl = "http://api.irishrail.ie/realtime/realtime.asmx/getStationDataByCodeXML?StationCode=$stationName"
        val stationDataRequest = StringRequest(
            Request.Method.GET, apiUrl,
            { response ->
                try {
                    //Create a JSON object containing information from the API.

                    Log.d("Mohit", "onCreate ${response}")
                    val xmlToJson = XmlToJson.Builder(response.toString()).build()
                    val stationDataModel = Gson().fromJson(xmlToJson.toString(), StationDataModel::class.java)
                    Log.d("Mohit", "xmlToJson ${xmlToJson}")
                    stationMutableLiveData.postValue(stationDataModel)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { volleyError ->
            Toast.makeText(context, volleyError.message, Toast.LENGTH_SHORT).show()
        }
        val requestQueue = Volley.newRequestQueue(context)
        stationDataRequest.setShouldCache(false)
        requestQueue.cache.clear()
        requestQueue.add(stationDataRequest)
    }
}
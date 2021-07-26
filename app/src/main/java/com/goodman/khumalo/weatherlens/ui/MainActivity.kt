package com.goodman.khumalo.weatherlens.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.goodman.khumalo.weatherlens.R
import com.goodman.khumalo.weatherlens.model.*
import com.goodman.khumalo.weatherlens.viewmodel.AccuWeatherViewModel
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private lateinit var  fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationStr: String
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var model: AccuWeatherModel
    private lateinit var viewModel: AccuWeatherViewModel
    private lateinit var cityName: TextView
    private lateinit var phrase: TextView
    private lateinit var temperature: TextView
    private lateinit var adapter: AccuWeatherDailyForecastAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityName = this.findViewById(R.id.city_name)
        phrase = this.findViewById(R.id.weather_phrase)
        temperature = this.findViewById(R.id.temperature)
        model = AccuWeatherModelImpl()
        viewModel = ViewModelProviders.of(this).get(AccuWeatherViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()
        recyclerView = this.findViewById(R.id.WeatherInfoList)
        setAdapter()
        setLiveDataListeners()

    }

    private fun setAdapter(){
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))
        adapter = AccuWeatherDailyForecastAdapter()
        recyclerView.adapter = adapter

    }
    private fun startBrowserForDetails(it: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }
    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 600000
        locationRequest.fastestInterval = 600000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    if (location != null) {
                        locationStr = "${location.latitude},${location.longitude}"
                        viewModel.getLocationKey(locationStr, model)
                    }
                }
            }
        }
    }



    //start location updates
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    override fun onResume() {
        super.onResume()
        val permissionAccessCoarseLocationApproved = ActivityCompat
            .checkSelfPermission(applicationContext!!, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

        if (permissionAccessCoarseLocationApproved)
            startLocationUpdates()
        else
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
    }
    private fun setLiveDataListeners() {
        viewModel.locationInfoLiveData.observe(this,
            { t ->
                cityName.text = t!!.englishName
                t.key?.let { viewModel.get5DailyForecast(it, model) }

                t.key?.let { viewModel.getHourlyForecast(it,model) }
            })

        viewModel.hourlyForecastLiveData.observe(this, object : Observer<MutableList<Weather12HourForecastResponse>>{
            override fun onChanged(t: MutableList<Weather12HourForecastResponse>?) {
                val get = t?.get(0)
                phrase.text = get?.iconPhrase ?: ""
                temperature.text = get?.temperature?.value.toString()+ 0x00B0.toChar()

            }

        })

        viewModel.fiveDayForecastLiveData.observe(this, object : Observer<Weather5DayForecastResponse>{
            override fun onChanged(t: Weather5DayForecastResponse?) {
                adapter.updateDailyForecast(t!!.dailyForeCasts)
                adapter.onItemClick = {
                    startBrowserForDetails(it)
                }
            }

        })
    }

}
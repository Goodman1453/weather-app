package com.goodman.khumalo.weatherlens.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.goodman.khumalo.weatherlens.R
import com.goodman.khumalo.weatherlens.databinding.ActivityMainBinding
import com.goodman.khumalo.weatherlens.viewmodel.AccuWeatherViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationStr: String
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var adapter: AccuWeatherDailyForecastAdapter
    private lateinit var viewModel: AccuWeatherViewModel
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(AccuWeatherViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()
        setAdapter()
        setLiveDataListeners()

    }

    private fun setAdapter() {
        bind.apply {
            bind.WeatherInfoList.addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    RecyclerView.VERTICAL
                )
            )
            adapter = AccuWeatherDailyForecastAdapter(this@MainActivity)
            bind.WeatherInfoList.adapter = adapter
        }

    }

    private fun startBrowserForDetails(it: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(it)
        startActivity(intent)
    }

    private fun getLocationUpdates() {
        locationRequest = LocationRequest.create()
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
                        viewModel.getLocationKey(locationStr)
                    }
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
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
            .checkSelfPermission(
                applicationContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
                PackageManager.PERMISSION_GRANTED

        if (permissionAccessCoarseLocationApproved)
            startLocationUpdates()
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                100
            )
    }

    private fun setLiveDataListeners() {
        viewModel.locationKey.observe(this,
            { t ->
                bind.cityName.text = t!!.englishName
                t.key?.apply {
                    let { viewModel.get5DailyForecast(it) }

                    let { viewModel.getHourlyForecast(it) }
                }

            })

        viewModel.hourlyForecast.observe(this,
            { t ->
                val get = t?.get(0)
                bind.weatherPhrase.text = get?.iconPhrase ?: ""
                (get?.temperature?.value.toString() + 0x00B0.toChar()).also {
                    bind.temperature.text = it
                }
            })

        viewModel.fiveDayForecast.observe(this,
            { t ->
                adapter.updateDailyForecast(t!!.dailyForeCasts)
                adapter.onItemClick = {
                    startBrowserForDetails(it)
                }
            })
    }

}
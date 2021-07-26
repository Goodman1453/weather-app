package com.goodman.khumalo.weatherlens.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.goodman.khumalo.weatherlens.R
import com.goodman.khumalo.weatherlens.model.*
import com.goodman.khumalo.weatherlens.viewmodel.AccuWeatherViewModel

class MainActivity : AppCompatActivity() {
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
        viewModel.getLocationKey("-28.711964,32.169778",model)
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
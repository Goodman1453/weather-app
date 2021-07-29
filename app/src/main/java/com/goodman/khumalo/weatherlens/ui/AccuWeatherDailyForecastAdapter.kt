package com.goodman.khumalo.weatherlens.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.goodman.khumalo.weatherlens.R
import com.goodman.khumalo.weatherlens.databinding.AccuDailyItemViewBinding
import com.goodman.khumalo.weatherlens.model.DayForecast
import com.goodman.khumalo.weatherlens.utils.DateFormatter.changeDateFormatToDate
import javax.inject.Inject

class AccuWeatherDailyForecastAdapter @Inject constructor(private val context: Context): RecyclerView.Adapter<AccuWeatherDailyForecastAdapter.ViewHolder>() {
    private lateinit var dayForecastList: MutableList<DayForecast>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: AccuDailyItemViewBinding = DataBindingUtil.inflate(layoutInflater ,
            R.layout.accu_daily_item_view, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (::dayForecastList.isInitialized) dayForecastList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayForecast = dayForecastList[position]
        holder.bind(dayForecast)
    }

    fun updateDailyForecast(dayForecastList: List<DayForecast>) {
        this.dayForecastList = dayForecastList.toMutableList()
        notifyDataSetChanged()
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val rowDailyForecastLayout: ViewGroup = itemView.findViewById(R.id.rowDailyForecastLayout)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val temp: TextView = itemView.findViewById(R.id.temp_high_low)


        fun bind(dayForecast: DayForecast) {

            date.text = changeDateFormatToDate(dayForecast.date)
            temp.text = dayForecast.temperature.minimum.value.toString()+"/"+dayForecast.temperature.maximum.value.toString()

            rowDailyForecastLayout.setOnClickListener {
                onItemClick?.invoke(dayForecast.mobileLink)
            }
        }
    }
}
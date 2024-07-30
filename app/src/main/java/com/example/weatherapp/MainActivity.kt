package com.example.weatherapp



import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//324bd9f23ac0d46f5f23582be6b51c5c
class MainActivity() : AppCompatActivity(), Parcelable {


    private lateinit var binding: ActivityMainBinding

    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchWeatherData("Navi Mumbai")
        SearchCity()
    }

    private fun SearchCity() {
        val searchView=binding.searchView2
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query : String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }


    private fun date(): String{
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }

    private fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    protected fun fetchWeatherData(cityname: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response =
            retrofit.getWeatherData(cityname, "324bd9f23ac0d46f5f23582be6b51c5c", "metric")
        response.enqueue(object : Callback<WeatherApp> {
                override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    //Log.d("DATA", "onResponse: $res")
                    val temperature = responseBody.main.temp.toString()
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise.toLong()
                    val sunSet = responseBody.sys.sunset.toLong()
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unknown"
                    changimageacctoweather(condition)
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min
                    val weather = responseBody.weather.firstOrNull()

                    val temp = findViewById<TextView>(R.id.temp)
                    temp.text = "$temperature ℃"

                    val humid = findViewById<TextView>(R.id.humidity)
                    humid.text = "$humidity %"

                    val wind = findViewById<TextView>(R.id.windSpeed)
                    wind.text = "$windSpeed m/s"

                    val sunR = findViewById<TextView>(R.id.sunRise)
                    sunR.text = "${time(sunRise)}"

                    val sunS = findViewById<TextView>(R.id.sunS)
                    sunS.text = "${time(sunSet)}"

                    val sea = findViewById<TextView>(R.id.seaLevel)
                    sea.text = "$seaLevel hPa"

                    val cond = findViewById<TextView>(R.id.cond)
                    cond.text = condition

                    val maxT = findViewById<TextView>(R.id.maxTemp)
                    maxT.text = "Max Temp: $maxTemp ℃"

                    val minT = findViewById<TextView>(R.id.minTemp)
                    minT.text = "Min Temp: $minTemp ℃"

                    val day = findViewById<TextView>(R.id.day)
                    day.text = dayName(System.currentTimeMillis())

                    val date = findViewById<TextView>(R.id.date)
                    date.text = date()

                    val cn= findViewById<TextView>(R.id.cityname)
                    cn.text = "$cityname"

                    val w = findViewById<TextView>(R.id.w)
                    w.text = "$condition"


                }


            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {

            }

        })
    }



    private fun changimageacctoweather(condition:String) {

        when(condition){
            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView4.setAnimation(R.raw.sun1)
            }
            "Partly Clouds","Clouds","Overcast","Mist","Foggy","Haze"->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView4.setAnimation(R.raw.cloud)
            }
            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain","Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView4.setAnimation(R.raw.rain)
            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard","Snow"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView4.setAnimation(R.raw.snow)
            }
            else ->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView4.setAnimation(R.raw.sun1)
        }
        }
        binding.lottieAnimationView4.playAnimation()


    }
}













package com.example.weather_app

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val city:String = "10001"
    val API:String= "8a316bae40ca552c86771c6d73150592"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherTask().execute()
    }


  inner class WeatherTask(): AsyncTask<String,Void,String>()
  {
      override fun onPreExecute() {
          super.onPreExecute()
         findViewById<ProgressBar>(R.id.loader).visibility= View.VISIBLE
          findViewById<RelativeLayout>(R.id.mainContainer).visibility= View.GONE
          findViewById<TextView>(R.id.errortext).visibility = View.GONE

      }

      override fun doInBackground(vararg p0: String?): String? {
          var response:String?
        try  {
              response=URL("https://api.openweathermap.org/data/2.5/weather?zip=$city&units=metric&appid=$API")
                  .readText(Charsets.UTF_8)
          }
        catch (e:Exception)
        {
            response = null
        }
          return response
      }

      override fun onPostExecute(result: String?) {
          super.onPostExecute(result)

          try{
              val jsonObj = JSONObject(result)
              val main =  jsonObj.getJSONObject("main")
              val wind =  jsonObj.getJSONObject("wind")
              val sys =  jsonObj.getJSONObject("sys")
              val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
              val updateAt:Long = jsonObj.getLong("dt")
              val updateAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyy hh:mm a", Locale.ENGLISH ).format(Date(updateAt*1000))
              val temp = main.getString("temp")+ "°C"
              val tempMin = "Min Temp: "+ main.getString("temp_Min")+ "°C"
              val tempMax = "Min Temp: "+ main.getString("temp_Max")+ "°C"
              val pressure = main.getString("Pressure")
              val humidity = main.getString("Humidity")
              val sunrise:Long= sys.getLong("sunrise")
              val sunset:Long= sys.getLong("Sunset")
              val windSpeed = main.getString("Speed")
              val WeatherDescription = weather.getString("description")
              val address = jsonObj.getString("name")+","+sys.getString("Country")

              findViewById<TextView>(R.id.address).text = address
              findViewById<TextView>(R.id.updated_at).text = updateAtText
              findViewById<TextView>(R.id.status).text = WeatherDescription.capitalize()
              findViewById<TextView>(R.id.temp).text = temp
              findViewById<TextView>(R.id.temp_min).text = tempMin
              findViewById<TextView>(R.id.temp_max).text = tempMax
              findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a ",Locale.ENGLISH).format(Date(sunrise*1000))
              findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a ",Locale.ENGLISH).format(Date(sunset*1000))
              findViewById<TextView>(R.id.Wind).text = windSpeed
              findViewById<TextView>(R.id.pressure).text = pressure
              findViewById<TextView>(R.id.humidity).text = humidity
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
              findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
          }
          catch (e:Exception)
          {
          findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
              findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE
          }
      }

  }
}


package com.example.geolocationlesson

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity(), LocationListener {

    val LOCATION_PERMITION_CODE = 2
    var granted: Boolean = false
    lateinit var location: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Для карт Yandex
        MapKitFactory.setApiKey("")

        //TODO проверка разрешения и запрос разрешения в случае отсутствия


        //TODO создайте объект класса LocationManager  и получите координаты

        //TODO обновить координаты местоположения

        findViewById<AppCompatButton>(R.id.map_button).setOnClickListener {
            val intent = Intent(this, MapActivityYandex::class.java)
            intent.putExtra("latitude", location.latitude)
            intent.putExtra("longitude", location.longitude)
            startActivity(intent)
        }

    }
    fun displayLocation(lat: Double, lon: Double){
        findViewById<TextView>(R.id.latitude).text = String.format("%.5f", lat)
        findViewById<TextView>(R.id.longitude).text = String.format("%.5f", lon)
    }

    //TODO функция определения реакции на выданные (или не выданные разрешения)


    override fun onLocationChanged(p0: Location) {
        val lat = p0.latitude
        val lon = p0.longitude
        displayLocation(lat, lon)
        Log.d("my", "lat " + lat + " long " + lon)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}
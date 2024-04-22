package com.example.geolocationlesson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapActivityYandex : AppCompatActivity() {
    private lateinit var mapView: MapView
    val mapObjectTapListener = object : MapObjectTapListener {
        override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
            Toast.makeText(
                applicationContext,
                "Вы нажали на маркер",
                Toast.LENGTH_SHORT
            ).show()
            return true
        }

    }
    private val tapListener = object : GeoObjectTapListener {
        override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {
            val selectionMetadata: GeoObjectSelectionMetadata = geoObjectTapEvent
                .geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)
            mapView.map.selectGeoObject(selectionMetadata)
            return false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_map_yandex)
        val latitude = intent.getDoubleExtra("latitude", 52.28697)
        val longitude = intent.getDoubleExtra("longitude", 104.30502)
        val startLocation = Point(latitude, longitude)
        mapView = findViewById(R.id.mapview)
        mapView.map.move(
            CameraPosition(
                startLocation,
                /* zoom = */ 17.0f,
                /* azimuth = */ 150.0f,
                /* tilt = */ 30.0f
            )
        )
        //установка маркера
        val marker = R.mipmap.ic_launcher
        val mapObjects = mapView.map.mapObjects
        val markObject = mapObjects.addPlacemark(
            startLocation,
            ImageProvider.fromResource(this, marker)
        )
        markObject.opacity = 0.5f
        markObject.setText("Сity")
        markObject.addTapListener(mapObjectTapListener)
    }
    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
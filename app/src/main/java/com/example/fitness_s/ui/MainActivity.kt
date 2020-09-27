package com.example.fitness_s.ui

import android.content.Intent
import android.os.Bundle
import com.example.fitness_s.R
import com.example.fitness_s.base.BaseMapActivity
import com.example.fitness_s.utils.ForegroundService
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMapActivity() {

    override fun getResId() = R.layout.activity_main
    override fun getMapViewId() = R.id.mapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
        stopForeground()
    }

    private fun setupListeners() {
        fab.setOnClickListener {
            map?.setStyle(Style.SATELLITE_STREETS)
        }
        btnStart.setOnClickListener {
            startForegroundService()
        }
        btnStop.setOnClickListener {
            stopForeground()
        }
    }

    private fun stopForeground(){
        val intent = Intent(this, ForegroundService::class.java)
        stopService(intent)
    }

    private fun startForegroundService(){
        val intent = Intent(this, ForegroundService::class.java)
        intent.putExtra("scnlk", "kjvklsd")
        startService(intent)
    }

}
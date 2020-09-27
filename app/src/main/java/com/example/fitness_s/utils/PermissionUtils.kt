package com.example.fitness_s.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity

object PermissionUtils {
    const val LOCATION_REQUEST_CODE = 101 //3)

    private val locationPermision = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)  // указыв на permission 0)


    fun requestLocationPermission(activity: AppCompatActivity): Boolean{ //2)
        if (checkLocationPermission(activity))
            return true

        activity.requestPermissions(locationPermision, LOCATION_REQUEST_CODE )
        return false
    }

    private fun checkLocationPermission(activity: AppCompatActivity) =                                  //1)
        activity.checkSelfPermission(locationPermision[0] ) == PackageManager.PERMISSION_GRANTED

}
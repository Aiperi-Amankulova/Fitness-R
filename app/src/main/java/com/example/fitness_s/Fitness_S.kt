package com.example.fitness_s

import android.app.Application
import com.example.fitness_s.data.PreferenceHelper


class Fitness_S: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.initPreference(applicationContext)
    }
}
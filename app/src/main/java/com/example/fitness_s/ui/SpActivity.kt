package com.example.fitness_s.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.fitness_s.R
import com.example.fitness_s.data.PreferenceHelper
import com.example.fitness_s.onBoarding.OnBoardingActivity

class SpActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp)
        Handler().postDelayed({
            selectActivity()
        },4000)

    }

    private fun selectActivity(){
        if (PreferenceHelper.getIsSecondLaunch()){
            startActivity(Intent(applicationContext, OnBoardingActivity:: class.java))
            finish()
        }
        else {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
    }
}
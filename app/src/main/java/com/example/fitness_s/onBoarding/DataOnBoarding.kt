package com.example.fitness_s.onBoarding

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DataOnBoarding (
    val img:Int,
    val firstText:String,
    val secondText: String
): Parcelable
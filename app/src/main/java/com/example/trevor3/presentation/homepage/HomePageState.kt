package com.example.trevor3.presentation.homepage

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.trevor3.domain.models.Tremor

@RequiresApi(Build.VERSION_CODES.O)
data class HomePageState (

    var index : Int = 0,
    var tremorCountDay : List<Long> = listOf(),
    var averageDuration : Double = 0.0,
    var averageIntensity : Double = 0.0,
    var selectedDay : String = "",
    var tremorCountWeek : ArrayList<Int> = arrayListOf(0),
    var tremorCountMonth : ArrayList<Int> = arrayListOf(0) ,
    var selectedWeek : Int = 0,
    var selectedMonth : Int = 0,
    var selectedYear: Int = 0


)
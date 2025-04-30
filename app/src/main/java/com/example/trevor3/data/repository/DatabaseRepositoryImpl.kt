package com.example.trevor3.data.repository

import com.example.trevor3.data.local.DatabaseHelper
import com.example.trevor3.domain.models.Tremor
import com.example.trevor3.domain.repository.IDatabaseRepository

class DatabaseRepositoryImpl(
    private val databaseHelper: DatabaseHelper
)  : IDatabaseRepository {



   // -------------------- DURATION FUNCTIONS -----------------
    override fun getAverageDurationForDay(day: String): Double? =
        databaseHelper.getAverageDurationForDay(day)

    override fun getAverageDurationForCurrentWeek(): Double? =
        databaseHelper.getAverageDurationForCurrentWeek()

    override fun getAverageDurationForSpecificWeek(year: Int, weekOfYear: Int): Double? =
        databaseHelper.getAverageDurationForSpecificWeek(year, weekOfYear)



    //------------ INTENSITY FUNCTIONS ---------------
    override fun getAverageIntensityForDay(year: Int, month: Int, day: Int): Double? =
        databaseHelper.getAverageIntensityForDay(year, month, day)

    override fun getAverageIntensityForSpecificWeek(year: Int, weekOfYear: Int): Double? =
        databaseHelper.getAverageIntensityForSpecificWeek(year, weekOfYear)


// -------------------- TREMOR FUNCTIONS --------------------------
    override fun getTremorCountForCurrentWeek(): ArrayList<Int> =
        databaseHelper.getTremorCountForCurrentWeek()

    override fun getTremorCountForSpecificDay(year: Int, month: Int, day: Int): Int =
        databaseHelper.getTremorCountForSpecificDay(year, month, day)

    override fun getTremorCountForSpecificWeek(year: Int, weekOfYear: Int): ArrayList<Int> =
        databaseHelper.getTremorCountForSpecificWeek(year, weekOfYear)

    override fun getTremorsByDate(date: String) =
        databaseHelper.getTremorsByDate(date)
}
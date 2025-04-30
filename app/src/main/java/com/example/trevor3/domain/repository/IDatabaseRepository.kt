package com.example.trevor3.domain.repository

import com.example.trevor3.domain.models.Tremor

interface IDatabaseRepository {

    fun getTremorsByDate(date: String): List<Tremor>
    fun getAverageDurationForDay(day: String): Double?
    fun getAverageDurationForCurrentWeek(): Double?
    fun getAverageDurationForSpecificWeek(year: Int, weekOfYear: Int): Double?
    fun getAverageIntensityForDay(year: Int, month: Int, day: Int): Double?
    fun getAverageIntensityForSpecificWeek(year: Int, weekOfYear: Int): Double?
    fun getTremorCountForCurrentWeek(): ArrayList<Int>
    fun getTremorCountForSpecificDay(year: Int, month: Int, day: Int): Int
    fun getTremorCountForSpecificWeek(year: Int, weekOfYear: Int): ArrayList<Int>



}
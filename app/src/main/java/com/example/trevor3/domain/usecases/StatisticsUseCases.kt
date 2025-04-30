package com.example.trevor3.domain.usecases

import com.example.trevor3.domain.repository.IDatabaseRepository
import javax.inject.Inject

data class StatisticsUseCases @Inject constructor(private val repository: IDatabaseRepository) {

    fun getAverageDurationForDay(day: String): Double? =
        repository.getAverageDurationForDay(day)

    fun getAverageDurationForCurrentWeek(): Double? =
        repository.getAverageDurationForCurrentWeek()

    fun getAverageDurationForSpecificWeek(year: Int, weekOfYear: Int): Double? =
        repository.getAverageDurationForSpecificWeek(year, weekOfYear)


    fun getAverageIntensityForDay(year: Int, month: Int, day: Int): Double? =
        repository.getAverageIntensityForDay(year, month, day)

    fun getAverageIntensityForSpecificWeek(year: Int, weekOfYear: Int): Double? =
        repository.getAverageIntensityForSpecificWeek(year, weekOfYear)


    fun getTremorCountForCurrentWeek(): ArrayList<Int> =
        repository.getTremorCountForCurrentWeek()

    fun getTremorCountForSpecificWeek(year: Int, weekOfYear: Int): ArrayList<Int> =
        repository.getTremorCountForSpecificWeek(year, weekOfYear)

    fun getTremorsByDate(date: String) =
        repository.getTremorsByDate(date)
}

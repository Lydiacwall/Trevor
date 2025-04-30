package com.example.trevor3.presentation.homepage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trevor3.domain.usecases.StatisticsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

import androidx.lifecycle.viewModelScope
import com.example.trevor3.domain.models.Tremor
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val statisticsUseCases: StatisticsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomePageState())
     val state: State<HomePageState> = _state

    init {
        setIndex(0)
        val today = LocalDate.now()
        viewModelScope.launch {

            val tremorList: List<Tremor> = (statisticsUseCases.getTremorsByDate(today.toString()))
            setTremorsByDate(extractStartTimes(tremorList))

            statisticsUseCases.getAverageDurationForDay(today.toString())
                ?.let { setAverageDuration(it) }
            statisticsUseCases.getAverageIntensityForDay(
                today.year,
                today.monthValue,
                today.dayOfMonth
            )
                ?.let { setAverageIntensity(it) }

            Log.d("HomeViewModel", "tremorList: $tremorList")


        }

    }

    fun onEvent(event: HomeEvent) {

        when (event) {
            is HomeEvent.DayWasSelected -> {
                viewModelScope.launch {

                    if(event.day != "") {
                        val tremorList: List<Tremor> =
                            (statisticsUseCases.getTremorsByDate(event.day))
                        setTremorsByDate(extractStartTimes(tremorList))

                        statisticsUseCases.getAverageDurationForDay(event.day)
                            ?.let { setAverageDuration(it) }

                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val date = sdf.parse(event.day)
                        val calendar = Calendar.getInstance().apply {
                            time = date
                        }
                        statisticsUseCases.getAverageIntensityForDay(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                            ?.let { setAverageIntensity(it) }

                        setSelectedDay(event.day)

                        setSelectedWeek(0)
                        setSelectedMonth(0)
                        setTremorsByWeek(arrayListOf())
                        setTremorsByMonth(arrayListOf())
                        setSelectedYear(0)
                        setIndex(0)
                    }

                }

            }


            is HomeEvent.OptionHasChanged -> {
                val today = LocalDate.now()
                if(event.index == 0){ // CURRENT DAY

                    viewModelScope.launch {

                        val tremorList: List<Tremor> = (statisticsUseCases.getTremorsByDate(today.toString()))
                        setTremorsByDate(extractStartTimes(tremorList))

                        statisticsUseCases.getAverageDurationForDay(today.toString())
                            ?.let { setAverageDuration(it) }
                        statisticsUseCases.getAverageIntensityForDay(
                            today.year,
                            today.monthValue,
                            today.dayOfMonth
                        )
                            ?.let { setAverageIntensity(it) }

                        setIndex(0)
                        setSelectedDay(today.toString())
                        setSelectedMonth(0)
                        setTremorsByWeek(arrayListOf())
                        setTremorsByMonth(arrayListOf())
                        setSelectedYear(0)

                    }
                }
                if (event.index == 1) { // CURRENT WEEK

                    viewModelScope.launch {

                        setTremorsByWeek(statisticsUseCases.getTremorCountForCurrentWeek())

                        statisticsUseCases.getAverageDurationForCurrentWeek()
                            ?.let { setAverageDuration(it) }


                        val weekFields = WeekFields.of(Locale.getDefault())
                        val currentWeek = today.get(weekFields.weekOfYear())

                        statisticsUseCases.getAverageIntensityForSpecificWeek(
                            today.year,
                            currentWeek
                        )
                            ?.let { setAverageIntensity(it) }

                        statisticsUseCases.getAverageDurationForCurrentWeek()
                            ?.let { setAverageDuration(it) }


                        setSelectedWeek(currentWeek)
                        setSelectedYear(today.year)
                        setIndex(1)
                        setSelectedDay("")
                        setSelectedMonth(0)
                        setTremorsByDate(listOf())
                        setTremorsByMonth(arrayListOf())
                    }
                }

            }
            is HomeEvent.WeekWasSelected -> {

                viewModelScope.launch {
                    val tremorList: ArrayList<Int> =
                        (statisticsUseCases.getTremorCountForSpecificWeek(event.year, event.week))

                    statisticsUseCases.getAverageDurationForSpecificWeek(event.year, event.week)
                        ?.let { setAverageDuration(it) }
                    statisticsUseCases.getAverageIntensityForSpecificWeek(event.year, event.week)
                        ?.let { setAverageIntensity(it) }


                    setTremorsByWeek(tremorList)
                    setSelectedWeek(event.week)
                    setSelectedYear(event.year)
                    setSelectedDay("")
                    setIndex(1)
                    setTremorsByDate(listOf())
                    setTremorsByMonth(arrayListOf())


                }

            }


            is HomeEvent.IndexChanged -> {
                viewModelScope.launch {

                    setIndex(event.index)

                }
            }
        }

    }

    private fun extractStartTimes(tremors: List<Tremor>): List<Long> {
        return tremors.map { it.startTime }
    }

    private fun setTremorsByDate(tremorsByDate: List<Long>) {
        _state.value = _state.value.copy(tremorCountDay = tremorsByDate)
    }

    private fun setAverageDuration(averageDuration: Double) {
        val durationInSeconds = averageDuration / 1000.0
        _state.value = _state.value.copy(averageDuration = durationInSeconds)
    }

    private fun setAverageIntensity(averageIntensity: Double) {
        _state.value = _state.value.copy(averageIntensity = averageIntensity)
    }

    private fun setTremorsByWeek(tremorsByWeek: ArrayList<Int>) {
        _state.value = _state.value.copy(tremorCountWeek = tremorsByWeek)
    }

    private fun setTremorsByMonth(tremorsByMonth: ArrayList<Int>) {
        _state.value = _state.value.copy(tremorCountMonth = tremorsByMonth)
    }


    private fun setSelectedMonth(month: Int) {
        _state.value = _state.value.copy(selectedMonth = month)
    }

    private fun setSelectedYear(year: Int) {
        _state.value = _state.value.copy(selectedYear = year)
    }

    private fun setSelectedWeek(week: Int) {
        _state.value = _state.value.copy(selectedWeek = week)
    }

    private fun setSelectedDay(day: String) {
        _state.value = _state.value.copy(selectedDay = day)
    }

    private fun setIndex(index: Int) {
        _state.value = _state.value.copy(index = index)
    }


}





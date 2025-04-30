package com.example.trevor3.presentation.homepage

sealed class HomeEvent {
    data class OptionHasChanged(val index: Int) : HomeEvent()
    data class DayWasSelected(val day: String) : HomeEvent()
    data class WeekWasSelected(val week: Int,val year: Int) : HomeEvent()
    data class IndexChanged(val index: Int) : HomeEvent()

}
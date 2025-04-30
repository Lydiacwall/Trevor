package com.example.trevor3.domain.models

data class Tremor(
    val id: Long,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val intensity: Float,
    val startDate: String,
    val endDate: String
)
package com.example.trevor3.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpForm (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
){}

package com.example.trevor3.data.model
import kotlinx.serialization.Serializable

@Serializable
class UserDto (
    val id: String,
    val firstName: String,
    val lastName : String,
    val password: String,
    val email: String
)
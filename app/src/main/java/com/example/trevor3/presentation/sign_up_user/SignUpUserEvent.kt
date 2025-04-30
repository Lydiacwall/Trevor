package com.example.trevor3.presentation.sign_up_user

sealed class SignUpUserEvent {
    data class AddUser(val firstName: String, val lastName: String, val email: String, val password: String, val verifyPassword: String): SignUpUserEvent()
    data class NameChanged(val name: String) : SignUpUserEvent()
    data class SurnameChanged(val surname: String) : SignUpUserEvent()
    data class EmailChanged(val email: String) : SignUpUserEvent()
    data class PasswordChanged(val password: String) : SignUpUserEvent()
    data class VerifiedPasswordChanged(val verifiedPassword: String) : SignUpUserEvent()
}
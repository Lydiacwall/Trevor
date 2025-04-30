package com.example.trevor3.presentation.login

import android.content.Context

sealed class LoginEvent {
    data class Validate(val password: String, val email: String, val context: Context): LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class PasswordVisibilityChanged(val noImportantString: String) : LoginEvent()
}
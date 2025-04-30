package com.example.trevor3.presentation.sign_up_user

data class SignUpUserState (
    val error: String? = null,
    val isSuccess: Boolean = false,
    val wrongField: String? = "",
    val nameInput: String = "",
    val surname: String = "",
    val emailInput: String = "",
    val passwordInput: String = "",
    val verifyPassword: String = "",
)
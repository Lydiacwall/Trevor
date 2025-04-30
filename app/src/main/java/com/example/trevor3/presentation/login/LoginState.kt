package com.example.trevor3.presentation.login

import com.example.trevor3.R
import com.example.trevor3.domain.models.User

data class LoginState (
    val email: String = "",
    val password: String = "",
    val user: User? = null,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val hiddenPassword: Boolean = true,
    val imageId: Int = R.drawable.hide_password_tr
)

package com.example.trevor3.presentation.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trevor3.R
import com.example.trevor3.data.local.DatabaseHelper
//import com.example.trevor3.data.local.UserDao
import com.example.trevor3.domain.models.Manager
import com.example.trevor3.domain.usecases.AuthenticateUser
import com.example.trevor3.domain.usecases.AuthenticationUseCases
//import com.example.trevor3.domain.usecases.LoginUser
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.AutoCloseable
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state;

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }

            is LoginEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }

            is LoginEvent.Validate -> {
                viewModelScope.launch {
                    val user = authenticationUseCases.authenticateUser(event.password, event.email)
                    if (user == null) {
                        _state.value = _state.value.copy(error = "No user found with the provided credentials.", isSuccess = false)
                    } else {
                        Manager.setUser(user)
                        val sharedPrefs = event.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPrefs.edit()
                            .putString("email", user.email)
                            .putString("password", user.password) // if you're using username/password auth
                            .apply()
                        Log.i(sharedPrefs.getString("email", null) + sharedPrefs.getString("password", null), "Logged in")
                        _state.value = _state.value.copy(user = user, isSuccess = true, error = null)
                    }                  }
                }

            is LoginEvent.PasswordVisibilityChanged -> {
                if(_state.value.hiddenPassword == true){
                    _state.value = _state.value.copy(hiddenPassword = false, imageId = R.drawable.show_password_tr)
                }
                else{
                    _state.value = _state.value.copy(hiddenPassword = true, imageId = R.drawable.hide_password_tr)
                }
            }
        }
    }
}



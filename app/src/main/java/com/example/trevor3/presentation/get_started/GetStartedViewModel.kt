package com.example.trevor3.presentation.get_started


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trevor3.domain.models.Manager
import com.example.trevor3.domain.usecases.AuthenticationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel() {

    fun tryAutoLogin(context: Context, onSuccess: () -> Unit, onFail: () -> Unit) {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val email = prefs.getString("email", null)
            val password = prefs.getString("password", null)

            if (email != null && password != null) {
                val user = authenticationUseCases.authenticateUser(password, email)
                if (user != null) {
                    Manager.setUser(user)
                    onSuccess()
                    return@launch
                }
            }
            onFail()
        }
    }
}

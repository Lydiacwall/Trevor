package com.example.trevor3.presentation.sign_up_user

import com.example.trevor3.domain.models.InvalidUserException
import com.example.trevor3.domain.models.Manager
import com.example.trevor3.domain.usecases.AuthenticationUseCases


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpUserViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = mutableStateOf(SignUpUserState())
    val state: State<SignUpUserState> = _state;

    fun onEvent(event: SignUpUserEvent) {
        when (event) {
            is SignUpUserEvent.AddUser -> {

                viewModelScope.launch {
                    try {
                        val user = authenticationUseCases.addUser(event.firstName, event.lastName, event.email, event.password)
                        _state.value = _state.value.copy(isSuccess = true, error = null)
                        Manager.setUser(user);
                    }
                    catch (exception: InvalidUserException) {
                        _state.value = _state.value.copy(isSuccess = false, wrongField = exception.message)
                        when(exception.message) {
                            "Email"-> {
                                _state.value = _state.value.copy(error = "The email cannot be empty !")
                            }
                            "Last Name"-> {
                                _state.value = _state.value.copy(error = "The last name cannot be empty !")
                            }
                            "First Name"-> {
                                _state.value = _state.value.copy(error = "The first name cannot be empty !")
                            }
                            "Password"-> {
                                _state.value = _state.value.copy(error = "The password cannot be empty !")
                            }
                            else -> _state.value = _state.value.copy(error = exception.message.toString())
                        }
                    }
                }
            }
            is SignUpUserEvent.EmailChanged -> {
                _state.value = _state.value.copy(emailInput = event.email)
            }
            is SignUpUserEvent.NameChanged -> {
                _state.value = _state.value.copy(nameInput = event.name)
            }
            is SignUpUserEvent.PasswordChanged -> {
                _state.value = _state.value.copy(passwordInput = event.password)
            }
            is SignUpUserEvent.SurnameChanged -> {
                _state.value = _state.value.copy(surname = event.surname)
            }
            is SignUpUserEvent.VerifiedPasswordChanged -> {
                _state.value = _state.value.copy(verifyPassword = event.verifiedPassword)
            }

        }
    }
}

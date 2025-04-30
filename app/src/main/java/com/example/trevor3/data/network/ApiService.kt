package com.example.trevor3.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.trevor3.data.model.SignUpForm
import com.example.trevor3.data.model.UserDto
import com.example.trevor3.data.model.Credentials


interface ApiService {
    @POST("api/User/login")
    suspend fun authenticateUser(@Body credentials: Credentials): Response<UserDto>

    @POST("api/User/SignUp")
    suspend fun addNewUser(@Body signUpForm: SignUpForm): Response<UserDto>


}
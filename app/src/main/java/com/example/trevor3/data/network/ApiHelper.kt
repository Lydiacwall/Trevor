package com.example.trevor3.data.network

import com.example.trevor3.data.model.Credentials
import com.example.trevor3.data.model.SignUpForm
import com.example.trevor3.domain.models.User
import com.example.trevor3.data.network.ApiService
import com.example.trevor3.domain.models.InvalidUserException
import com.example.trevor3.domain.models.Mapper.toDomain

class ApiHelper(private val apiService: ApiService) {

    suspend fun authenticate(credentials: Credentials): User? {
        try {
            val response = apiService.authenticateUser(credentials)
            return if (response.isSuccessful && response.body() != null) {
                // You can access the HTTP status code if needed
                val statusCode = response.code()
                println(statusCode)
                val userDto = response.body()
                println((response.body()!!.id)?.toString() ?: "No ID available")
                userDto?.toDomain()
            } else {
                // Log error response
                val errorBody = response.errorBody()?.string()
                println("Error during authentication: ${response.code()} - $errorBody")
                null
            }
        } catch (e: Exception) {
            println("Network error: ${e.localizedMessage}")
            return null
        }
    }

    @Throws(InvalidUserException::class)
    suspend fun signUpUser(signUpForm: SignUpForm): User? {
        try {
            val response = apiService.addNewUser(signUpForm)
            return if (response.isSuccessful && response.body() != null) {
                // You can access the HTTP status code if needed
                val statusCode = response.code()
                println(statusCode)
                val userDto = response.body()
                println(println(response.body()!!.id)?.toString() ?: "No ID available")
                userDto?.toDomain()
            } else if (response.code() == 409) {
                // Log error response
                val errorBody = response.errorBody()?.string()
                println("Error during signing up: ${response.code()} - $errorBody")
                throw InvalidUserException("There is already a user with the same account")
            } else {
                null
            }
        } catch (e: Exception) {
            println("Network error: ${e.localizedMessage}")
            throw e;
        }
    }
}
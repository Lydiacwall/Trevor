package com.example.trevor3.domain.usecases

import com.example.trevor3.domain.models.InvalidUserException
import com.example.trevor3.domain.models.User
import com.example.trevor3.domain.repository.IUserRepository

class AuthenticateUser(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(password: String, email: String): User? {
        if (password.isBlank()) {
            throw InvalidUserException("The password cannot be empty !!!")
        }
        if (email.isBlank()) {
            throw InvalidUserException("The email cannot be empty !!!")
        }
        val user = repository.getUserByPasswordAndEmail(password, email);
        return user;
    }
}
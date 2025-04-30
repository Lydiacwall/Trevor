package com.example.trevor3.domain.usecases

import com.example.trevor3.domain.models.InvalidUserException
import com.example.trevor3.domain.models.User
import com.example.trevor3.domain.repository.IUserRepository

class AddUser(
    private val repository: IUserRepository
) {

    @Throws(InvalidUserException::class)
    suspend operator fun invoke(firstName: String, lastName: String, email: String, password: String): User? {
        try {
            if (firstName.isBlank()) {
                throw InvalidUserException("First Name")
            }
            if (lastName.isBlank()) {
                throw InvalidUserException("Last Name")
            }
            if (email.isBlank()) {
                throw InvalidUserException("Email")
            }
            if(!email.endsWith("@gmail.com")){
                throw InvalidUserException("Email must be gmail for statistics !")
            }
            if (password.isBlank()) {
                throw InvalidUserException("Password")
            }
            return repository.addNewUser(firstName= firstName, lastName= lastName, email= email, password= password)
        } catch (e: Exception) {
            throw e;
        }
    }
}
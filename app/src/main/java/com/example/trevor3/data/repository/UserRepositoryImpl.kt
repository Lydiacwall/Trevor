package com.example.trevor3.data.repository

//import com.example.trevor3.data.local.UserDao
//import com.example.trevor3.data.model.UserEntity
import com.example.trevor3.data.model.Credentials
import com.example.trevor3.data.model.SignUpForm
import com.example.trevor3.data.network.ApiHelper
import com.example.trevor3.domain.models.InvalidUserException
import com.example.trevor3.domain.models.User
import com.example.trevor3.domain.repository.IUserRepository

//import com.example.trevor3.domain.repository.IUserRepository


class UserRepositoryImpl(private val userApi : ApiHelper)
    : IUserRepository {

    override suspend fun getUserByPasswordAndEmail(password: String, email: String): User? {
        return userApi.authenticate(Credentials(email, password));
    }

    @Throws(InvalidUserException::class)
    override suspend fun addNewUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): User? {
        try {
            return userApi.signUpUser(
                SignUpForm(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                )
            )
        } catch (e: Exception) {
            throw e;
        }
    }

}
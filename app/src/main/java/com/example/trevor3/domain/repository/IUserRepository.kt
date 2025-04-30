package com.example.trevor3.domain.repository

import com.example.trevor3.domain.models.User
//
interface IUserRepository {
    suspend fun getUserByPasswordAndEmail(password: String, email: String): User?
    suspend fun addNewUser(firstName: String, lastName: String, email: String, password: String): User?
}
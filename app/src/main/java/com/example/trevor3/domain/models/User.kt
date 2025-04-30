package com.example.trevor3.domain.models

/**
 * Purpose: Represents the business logic model or core domain object in the app.
 * Usage: Used within the domain layer and presentation layer. It’s the model
 * that the app interacts with for business logic.
 * Usages : in UseCases. It is passed between the domain and the presentation
 * layers
 */

data class User (
    val id: String,
    val firstName: String,
    val lastName : String,
    val password: String,
    val email: String

){
    override fun toString(): String {
        return "First name: $firstName last name: $lastName  email: $email";
    }
}

class InvalidUserException(message: String): Exception(message)
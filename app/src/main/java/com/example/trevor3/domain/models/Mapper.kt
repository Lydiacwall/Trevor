package com.example.trevor3.domain.models

import com.example.trevor3.data.model.UserDto

object Mapper {
    fun UserDto.toDomain(): User {
        return User(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            password = this.password,
            email = this.email,

        )
    }
}
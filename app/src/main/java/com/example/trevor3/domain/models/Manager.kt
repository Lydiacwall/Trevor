package com.example.trevor3.domain.models

object Manager {
    var currentUser: User? = null

    fun setUser(user: User?) {

        currentUser = user
    }
}


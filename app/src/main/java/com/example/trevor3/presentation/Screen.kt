package com.example.trevor3.presentation

sealed class Screen(val route: String) {
    data object GetStartedScreen: Screen("get_started_screen")
    data object LoginScreen: Screen("login_screen")
    data object AddUserScreen: Screen("add_user_screen")
    data object HomePageScreen: Screen("home_page_screen")

}
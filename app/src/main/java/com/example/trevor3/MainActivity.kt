package com.example.trevor3
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trevor3.data.local.DatabaseHelper
import com.example.trevor3.data.local.TremorDetectionService
import com.example.trevor3.presentation.get_started.GetStartedView
import com.example.trevor3.presentation.homepage.HomePageView
import com.example.trevor3.ui.theme.Trevor3Theme
import com.example.trevor3.presentation.login.SignInView
import com.example.trevor3.presentation.sign_up_user.SignUpUserView
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import com.example.trevor3.domain.models.Manager
import com.example.trevor3.presentation.Screen

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var databaseHelper: DatabaseHelper // Get database instance



    @RequiresApi(Build.VERSION_CODES.O)
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        databaseHelper = DatabaseHelper(this)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val permission = Manifest.permission.ACTIVITY_RECOGNITION
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
            } else {
                startTremorService()
            }
        } else {
            startTremorService()
        }

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedEmail = prefs.getString("email", null)
        val savedPassword = prefs.getString("password", null)




        enableEdgeToEdge()
        setContent {
            Trevor3Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val startRoute = com.example.trevor3.presentation.Screen.GetStartedScreen.route
                    if (savedEmail != null && savedPassword != null) {
                        val startRoute = com.example.trevor3.presentation.Screen.HomePageScreen.route
                    }


                    NavHost(
                        navController = navController,

                        startDestination = startRoute
                    ) {
                        composable(
                            route = com.example.trevor3.presentation.Screen.GetStartedScreen.route
                        )
                        {
                            GetStartedView(navController = navController)
                        }
                        composable(
                            route = com.example.trevor3.presentation.Screen.LoginScreen.route
                        )
                        {
                            SignInView(navController = navController)//, viewModel = loginViewModel)
                        }
                        composable(
                            route = com.example.trevor3.presentation.Screen.HomePageScreen.route
                        )
                        {
                            if (Manager.currentUser != null) {
                                HomePageView(navController = navController)
                            } else {
                                // Redirect to login if not authenticated
                                LaunchedEffect(Unit) {
                                    navController.navigate(Screen.LoginScreen.route) {
                                        popUpTo(Screen.HomePageScreen.route) { inclusive = true }
                                    }
                                }
                            }
                        }
                        composable(
                            route = com.example.trevor3.presentation.Screen.AddUserScreen.route
                        ) {
                            SignUpUserView(navController = navController)
                        }


                    }
                }
            }

        }
    }


    private fun startTremorService() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        if (!prefs.getBoolean("tremor_service_started", false)) {
            val serviceIntent = Intent(this, TremorDetectionService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
            prefs.edit().putBoolean("tremor_service_started", true).apply()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTremorService()
        }
    }
}




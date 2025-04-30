package com.example.trevor3
import androidx.compose.material3.ExperimentalMaterial3Api
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.trevor3.presentation.GetStartedView
import com.example.trevor3.presentation.homepage.HomePageView
import com.example.trevor3.ui.theme.Trevor3Theme
import com.example.trevor3.presentation.login.SignInView
import com.example.trevor3.presentation.sign_up_user.SignUpUserView
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest

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

        enableEdgeToEdge()
        setContent {
            Trevor3Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = com.example.trevor3.presentation.Screen.HomePageScreen.route
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
                            HomePageView(navController = navController)
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




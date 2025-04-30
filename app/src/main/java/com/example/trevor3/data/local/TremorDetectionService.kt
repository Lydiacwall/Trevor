package com.example.trevor3.data.local

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.math.abs
import kotlin.math.sqrt

class TremorDetectionService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // Variables to track tremor event timing
    private var isTremorOngoing = false
    private var tremorStartTime: Long = 0
    private var tremorEndCandidateTime: Long = 0
    private val tremorCooldownMillis = 6000L

    // Detection threshold, adjust as needed
    private val threshold = 15f

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Ensure the service continues running until explicitly stopped.
        return START_STICKY
    }


    private fun startForegroundService() {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("tremor_channel", "Tremor Detection Service")
        } else {
            ""
        }
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Tremor Detection")
            .setContentText("Monitoring device vibrations in the background")
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
        val notification: Notification = notificationBuilder.build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)
        }
        return channelId
    }

    //The onSensorChanged method processes sensor updates and uses simple thresholds to detect tremors.
    // Adjust the threshold and sampling rate based on your testing.

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

         val gravity = FloatArray(3) { 0f } // gravity on x, y, z
         val linear = FloatArray(3) { 0f }  // filtered acceleration (no gravity)
         val alpha = 0.8f

        val rawX = event.values[0]
        val rawY = event.values[1]
        val rawZ = event.values[2]

        // Apply high-pass filter to remove gravity
        gravity[0] = alpha * gravity[0] + (1 - alpha) * rawX
        gravity[1] = alpha * gravity[1] + (1 - alpha) * rawY
        gravity[2] = alpha * gravity[2] + (1 - alpha) * rawZ

        val x = rawX - gravity[0]
        val y = rawY - gravity[1]
        val z = rawZ - gravity[2]

        val currentTime = System.currentTimeMillis()

        if (isTremorDetected(x, y, z)) {
            // If tremor is detected and not already ongoing, mark the start time
            if (!isTremorOngoing) {
                isTremorOngoing = true
                tremorStartTime = currentTime
            }
            tremorEndCandidateTime = 0

        } else if (isTremorOngoing) {
            // No motion, but tremor was ongoing
            if (tremorEndCandidateTime == 0L) {
                tremorEndCandidateTime = currentTime
            }
            if (currentTime - tremorEndCandidateTime > tremorCooldownMillis) {
                val intensity = calculateIntensity(x, y, z)
                recordTremorEvent(tremorStartTime, currentTime, intensity)
                isTremorOngoing = false
                tremorEndCandidateTime = 0
            }


        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for tremor detection in this use case
    }

    // Example tremor detection logic (adjust threshold and logic as needed)
    private fun isTremorDetected(x: Float, y: Float, z: Float): Boolean {
        // Simple threshold logic; you might add more sophisticated filtering or hysteresis here
        return abs(x) > threshold || abs(y) > threshold || abs(z) > threshold
    }

    // Example calculation for tremor intensity
    private fun calculateIntensity(x: Float, y: Float, z: Float): Float {
        return sqrt((x * x + y * y + z * z).toDouble()).toFloat()
    }

    // Placeholder for recording the tremor event into the local SQLite database
    private fun recordTremorEvent(startTime: Long, endTime: Long, intensity: Float) {
        // For example, call a method from your DatabaseHelper:
         val dbHelper = DatabaseHelper(applicationContext)
        dbHelper.insertTremor(startTime, endTime, intensity)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Return null because this is a started service, not a bound service.
        return null
    }


}



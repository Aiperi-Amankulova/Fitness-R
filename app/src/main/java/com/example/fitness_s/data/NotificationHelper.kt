package com.example.fitness_s.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fitness_s.R
import com.example.fitness_s.utils.ForegroundService
import com.example.fitness_s.utils.ForegroundService.Companion.STOP_SERVICE_ACTION

object NotificationHelper {
    private const val CHANNEL_ID = "CHANNEL_ID"

    fun createNotification(context: Context): Notification? {

        createNotificationChannel(context)

        val intent = Intent(context, ForegroundService::class.java
        )
        val pIntent = PendingIntent.getService(context, 0 ,intent,0)
        intent.action =STOP_SERVICE_ACTION

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.mapbox_compass_icon)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .addAction(R.drawable.mapbox_compass_icon,"stop",pIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "fvbfb"
            val descriptionText = "gfdgdfgdgdfg"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
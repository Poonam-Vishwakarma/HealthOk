package com.vcreate.healthok.ui.main.user.home.medicationreminder

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vcreate.healthok.R

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val builder = NotificationCompat.Builder(context, "HealthOk")
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle("Hello")
            .setContentText("Hello i am vahid")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val nmc = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Please Enable Permission", Toast.LENGTH_SHORT).show()
            return
        }
        nmc.notify(200, builder.build())
    }
}
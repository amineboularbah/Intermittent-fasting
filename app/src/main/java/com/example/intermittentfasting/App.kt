package com.example.intermittentfasting
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                timetrackingchannel,
                "Time tracking",
                NotificationManager.IMPORTANCE_LOW
            )
            val faststat = NotificationChannel(
                faststatuschannel,
                "Fast started/finished",
                NotificationManager.IMPORTANCE_HIGH
            )
            faststat.description = "Your fast status"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            manager.createNotificationChannel(faststat)

        }
    }

    companion object {
        const val timetrackingchannel = "time tracker notif"
        const val faststatuschannel = "fast status"

    }
}
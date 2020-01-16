package com.example.intermittentfasting
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.core.app.NotificationManagerCompat
import com.example.intermittentfasting.App.Companion.faststatuschannel
import com.example.intermittentfasting.App.Companion.timetrackingchannel
import android.content.ContentResolver
import android.net.Uri


@Suppress("UNREACHABLE_CODE", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NAME_SHADOWING"
)
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class TimeLeftToeat : Service() {
    private var notificationManager: NotificationManagerCompat? = null
    var fastend = false
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var input = intent.getStringExtra("inputExtra").toLong()
        val message = intent.getStringExtra("message")
        val message2 = intent.getStringExtra("message2")

        notificationManager = NotificationManagerCompat.from(this)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        object : CountDownTimer(input, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                fastend = false
                input = millisUntilFinished
                val hours = TimeUnit.MILLISECONDS.toHours(input)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(input))
                val seconds = TimeUnit.MILLISECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(input))
                val timeLeft = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds)
                val notification = NotificationCompat.Builder(this@TimeLeftToeat, timetrackingchannel)
                    .setContentTitle("Intermittent Fasting")
                    .setContentText(message + timeLeft)
                    .setSmallIcon(R.drawable.ic_restaurant_black_24dp)
                    .setContentIntent(pendingIntent)
                    .build()

                startForeground(21, notification)
            }
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                stopSelf()
                val notint = Intent(this@TimeLeftToeat, SSaveFast::class.java)
                val pedint =PendingIntent.getActivity(
                    this@TimeLeftToeat,
                    0, notint, 0
                )
                val notification = NotificationCompat.Builder(this@TimeLeftToeat, faststatuschannel)
                    .setSmallIcon(R.drawable.ic_restaurant_black_24dp)
                    .setContentTitle("Intermittent Fasting")
                    .setContentText(message2)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pedint)
                    .setAutoCancel(true)
                    .build()
                notificationManager!!.notify(21, notification)

            }
        }.start()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}
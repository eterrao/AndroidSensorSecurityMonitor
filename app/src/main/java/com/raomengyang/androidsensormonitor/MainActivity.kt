package com.raomengyang.androidsensormonitor

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.raomengyang.androidsensormonitor.ui.IUIFactory
import com.raomengyang.androidsensormonitor.utils.CameraChecker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IUIFactory {
    private val log_tag: String? = MainActivity::class.java.simpleName

    override fun openCamera() {
        var cameraChecker: CameraChecker = CameraChecker()
        Log.e(log_tag, "camera open : ${cameraChecker.isCameraUsebyApp()}")
        Toast.makeText(applicationContext,"camera open : ${cameraChecker.isCameraUsebyApp()}", Toast.LENGTH_LONG).show()
    }

    override fun initView() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            openCamera()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    override fun createNotification() {
        var notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channelId = "sensor_monitor"
        var mChannel = NotificationChannel(channelId, "SensorMonitor", importance)
        // Configure the notification channel.
        val description = "Android sensor monitor"
        mChannel.description = description
        mChannel.enableLights(true)
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager.createNotificationChannel(mChannel)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(description)
                .setContentText(description)
                .setLights(-0xff0100, 2000, 2000)
        var pendingIT = Intent(this@MainActivity, MainActivity::class.java)
        pendingIT.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val contentIntent = PendingIntent.getActivity(applicationContext, 0, pendingIT, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(contentIntent)
        notificationManager.notify(1, notificationBuilder.build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        createNotification()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

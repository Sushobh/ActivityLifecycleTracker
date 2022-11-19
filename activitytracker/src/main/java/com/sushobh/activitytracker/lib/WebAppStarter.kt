package com.sushobh.activitytracker.lib

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ranrings.libs.androidapptorest.AndroidRestServer
import com.ranrings.libs.androidapptorest.Base.GetRequestHandler
import com.ranrings.libs.androidapptorest.ReactWebApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val WEB_APP_FOLDER_NAME = "asohdiausgdy8aisgdyu8asd"

internal class WebAppStarter {

    private val dispatcher = Dispatchers.IO
    private val scope = CoroutineScope(dispatcher)
    private var androidRestServer: AndroidRestServer? = null
    private val NOTIFICATION_CHANNEL_ID = "213123"
    private val port = 8080


    private fun createNotificationChannel(application: Application) {
        val name: CharSequence = application.getString(R.string.channel_name)
        val description: String = application.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
        channel.description = description
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager: NotificationManager = application.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun displayNotification(application: Application) {
        val builder = NotificationCompat.Builder(
            application,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_wood)
            .setContentTitle("View Logs here")
            .setOngoing(true)
            .setContentText(getIpAddress(application) + ":" + port + "/actcycle")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(application)
        notificationManager.notify(100, builder.build())
    }

    private fun getIpAddress(application: Application): String {
        val wifiMgr = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiMgr.connectionInfo
        val ip = wifiInfo.ipAddress
        return Formatter.formatIpAddress(ip)
    }

    fun stop() {
        androidRestServer?.stop()
    }

    fun startWebApp(application: Application) {
        scope.launch {
            val buildFolderStream = application.assets.open("build.zip")
            androidRestServer = AndroidRestServer.Builder()
                .startWebApp(true)
                .setApplication(application)
                .addWebApp(
                    ReactWebApp(
                        application,
                        buildFolderStream,
                        "actcycle",
                        WEB_APP_FOLDER_NAME
                    )
                )
                .addRequestHandler(object : GetRequestHandler<Any>() {
                    override fun onGetRequest(uri: String): Any {
                        return Json.encodeToString(ActTracker.getEventsForApi())
                    }

                    override fun getMethodName(): String {
                        return "getlifecycleevents"
                    }

                })
                .addRequestHandler(object : GetRequestHandler<Any>() {
                    override fun onGetRequest(uri: String): Any {
                        return Json.encodeToString(ActTracker.getCurrentlyStoredEvents())
                    }

                    override fun getMethodName(): String {
                        return "getalllifecycleevents"
                    }

                })
                .setPort(port)
                .build()
            androidRestServer?.start()
            createNotificationChannel(application)
            displayNotification(application)
        }

    }

}
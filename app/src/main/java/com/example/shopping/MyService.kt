package com.example.shopping

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap


object MyServiceObject {
    val NOTIFICATION_ID: Int = 2;
    val CHANNEL_NAME = "MyService"
    var applicationContext: Context? = null

    private val LOG_TAG: String = MyService::class.java.getName()

    fun isServiceRunning(context: Context, serviceClassName: String): Boolean {
        val services: List<ActivityManager.RunningServiceInfo> =
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .getRunningServices(Int.MAX_VALUE)

        for (runningServiceInfo in services) {
            Log.i("services", runningServiceInfo.service.getClassName());

            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true
            }
        }
        return false
    }
}

class MyService : Service() {
    lateinit var player: MediaPlayer;
    lateinit var myReceiver: MyReceiver;
    lateinit var intentfilter: IntentFilter;

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("service", intent.toString())
        /**
         * get service class name to check if myserivce is running
         */
        val serviceClassName = MyService::class.qualifiedName!!;
        val isRunning = MyServiceObject.isServiceRunning(MyServiceObject.applicationContext!!, serviceClassName);
        Log.d("service", "isRunning $isRunning")
        if (isRunning) {
            Toast.makeText(MyServiceObject.applicationContext, "show by service", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    override fun onCreate() {
        Log.d("service", "onCreated")

        super.onCreate()
        /**
         * assing the application for later use case
         */
        MyServiceObject.applicationContext = applicationContext;
        /**
         * create player for media, set loop to true then start play
         */
        player = MediaPlayer.create(applicationContext, Settings.System.DEFAULT_ALARM_ALERT_URI);
        player.setLooping(true);
        player.start();

        /**
         * broadcast
         */
        intentfilter = IntentFilter( "${packageName}.SEND_MESSAGE")
        myReceiver = MyReceiver();
        registerReceiver(myReceiver, intentfilter)

        /**
         * change wallpaper
         */
        setWallpaper();

        /**
         * minimize app
         */
        minimizeApp();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            newVersionStartForeground()
        } else {
            startForeground(1, Notification())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun newVersionStartForeground(): Unit {
        Log.d("service", "newVersionStartForeground")
        val notificationChannel =
            NotificationChannel(packageName, MyServiceObject.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
        manager.createNotificationChannel(notificationChannel)
        val notificationBuilder = NotificationCompat.Builder(this, packageName)

        val title = "app is running";

        val notification = notificationBuilder
            .setOngoing(true)
            .setContentTitle(title)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build();
        startForeground(MyServiceObject.NOTIFICATION_ID, notification);

    }


    private fun setWallpaper(): Unit {
        val bitmap: Bitmap? =
            ContextCompat.getDrawable(MyServiceObject.applicationContext!!, R.drawable.wallpaper)?.toBitmap()
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        wallpaperManager.setBitmap(bitmap)
    }

    private  fun minimizeApp() : Unit{
        var startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(startMain)
    }


    override fun onDestroy() {
        Log.d("service", "onDestroy")
        /**
         * - stop player
         * - remove broadcast reciever
         * - reset wallpaper
         */
        player.stop();
        unregisterReceiver(myReceiver)
        val wallpaperManager = WallpaperManager.getInstance(MyServiceObject.applicationContext)
        wallpaperManager.clearWallpaper();
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("service", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d("service", "onRebind")
        super.onRebind(intent)
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("service", "onBind")
        return null;
    }
}
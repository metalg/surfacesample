package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val TAG: String? = "VAActivity"
    val ACTION_START = "com.thalesifec.exoplayer.announcements.START"
    val ACTION_RESUME = "com.thalesifec.exoplayer.announcements.RESUME"
    val ACTION_STOP = "com.thalesifec.exoplayer.announcements.STOP"
    val ACTION_PAUSE = "com.thalesifec.exoplayer.announcements.PAUSE"
    val EXTRA_HAS_VA_VOE_VOR_PENDING = "com.thalesifec.framework.tmp.HAS_VA_VOE_VOR_PENDING"

    val INTENT_PARAMETER_SOURCE = "SOURCE"
    val INTENT_PARAMETER_MEDIA_TYPE = "MEDIA_TYPE"

    private val myreceiver = AnnouncementReceiver()

    inner class AnnouncementReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val mediaType = intent?.getStringExtra(INTENT_PARAMETER_MEDIA_TYPE)

            when (intent?.action) {
                ACTION_PAUSE -> {
                    Log.d(TAG,"Pausing for $mediaType")
                    // Do nothing.
                }
                ACTION_RESUME -> {
                    Log.d(TAG,"Resuming for $mediaType")
                }
                ACTION_STOP -> {
                    val hasVaVorVoePending = intent.getBooleanExtra(EXTRA_HAS_VA_VOE_VOR_PENDING, false)
                    Log.d(TAG,"Stopping for $mediaType: $hasVaVorVoePending")
                    if (!hasVaVorVoePending) {
                        finish()
                        exitProcess(0)
                    }
                }
                else -> Log.d(TAG,"Unhandled Action ${intent?.action}")
            }
        }

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"GLI onCreate hello")
        registerReceiver(myreceiver, IntentFilter().apply {
            addAction(ACTION_STOP)
            addAction(ACTION_RESUME)
            addAction(ACTION_PAUSE)
        })
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"GLI onPause bye")
        finish()
        Log.d(TAG,"bye")
        exitProcess(0)
    }
}
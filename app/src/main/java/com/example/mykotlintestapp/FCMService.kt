package com.example.mykotlintestapp

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCMService","From: ${message?.from}")
        message?.data?.let {
            Log.d("FCMService","Message data payload: "+message.data)
        }
        message?.notification?.let {
            Log.d("FCMService", "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCMService", "token: $token")
        super.onNewToken(token)
    }
}
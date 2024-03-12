package com.example.mykotlintestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mykotlintestapp.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val TOPIC = "mybroadcasttopic"
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true



        subscribeToTopic()


    }

    private fun subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener{
                var msg =""
                if(it.isSuccessful){
                    msg="successful"
                }else{
                    msg = "unsuccessful"
                }
                Toast.makeText(this,getString(R.string.message_subscribe,TOPIC,msg),Toast.LENGTH_SHORT).show()


            }
    }

}
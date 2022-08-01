package ru.example.customintentproect.intent

import android.content.Intent
import android.widget.Toast
import ru.example.customintentproect.app

class CustomIntentServiceImp:CustomIntentService() {


    override fun onHandleIntent(intent: Intent) {
        var status = intent?.getStringExtra("status")
        Toast.makeText(app,"Response status: " + status,Toast.LENGTH_SHORT).show()
    }
}
package ru.example.customintentproect.intent

import android.util.Log

class CustomRunnable:Runnable {

    var counter: Int = 0

    var url: String = ""

    var stopFlag: Boolean = true


    override fun run() {
        while (stopFlag) {
            Log.d("###", "You have viewed $counter pictures of dogs")
            counter += 1
            //    Log.d("###", "The last picture url is $url")
        }
    }
}
package ru.example.customintentproect.intent

import android.os.Handler
import android.os.Message
import android.util.Log

class CustomThread(val handler: Handler) : Thread() {

    var count: Int = 0
    var stopFlag = true


    private fun showError(): Boolean {
        Log.d("###", "error section")
        return true
    }

    private fun updateUI() {
        Log.d("###", "update section")
    }

    override fun run() {
        //  Toast.makeText(app, "hahaha", Toast.LENGTH_SHORT).show()
        while (stopFlag) {
            Log.d("###", "You click $count times")
            var msg: Message
            msg = handler.obtainMessage(0, count, 0)
            handler.sendMessage(msg)
            sleep(5000)
        }
    }


    fun post() {
        count = count
    }
    fun post(i: Int) {
        count = i
    }
    fun quit() {
        stopFlag = false
    }
    fun getCounter(): Int {
        return count
    }
}
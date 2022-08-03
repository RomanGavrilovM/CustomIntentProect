package ru.example.customintentproect.intent

import android.util.Log

class CustomThread : Thread() {
    var count: Int = 0
    var stopFlag = true

    override fun run() {

        while (stopFlag) {
            Log.d("###", "You click $count times")

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

    fun getCounter():Int{
        return count
    }

}